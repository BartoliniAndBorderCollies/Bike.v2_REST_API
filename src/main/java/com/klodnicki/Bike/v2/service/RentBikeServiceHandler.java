package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.service.api.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RentBikeServiceHandler implements RentBikeServiceApi {

    private final BikeServiceApi bikeService;
    private final ChargingStationServiceApi chargingStationService;
    private final UserServiceApi userService;
    private final ModelMapper modelMapper;
    private final RentRepository rentRepository;

    @Override
    public RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO) {
        Rent rent = findRentById(id);

        if (rentRequestDTO.getDaysOfRent() > 0) {
            rent.setDaysOfRent(rentRequestDTO.getDaysOfRent());
        }
        rentRepository.save(rent);
        return modelMapper.map(rent, RentResponseDTO.class);
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        List<Bike> availableBikes = bikeService.findByIsRentedFalse();
        List<BikeForNormalUserResponseDTO> bikesForNormalUserDTO = new ArrayList<>();

        for (Bike bike : availableBikes) {

            BikeForNormalUserResponseDTO bikeForNormalDTO = BikeForNormalUserResponseDTO.builder()
                    .id(bike.getId())
                    .serialNumber(bike.getSerialNumber())
                    .isRented(bike.isRented())
                    .bikeType(bike.getBikeType())
                    .build();

            bikesForNormalUserDTO.add(bikeForNormalDTO);

        }
        return bikesForNormalUserDTO;
    }

    @Override
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id) {
        Bike bike = bikeService.findBikeById(id);
        return modelMapper.map(bike, BikeForNormalUserResponseDTO.class);
    }

    @Override
    @Transactional
    public RentResponseDTO rent(RentRequest rentRequest) {
        Bike bike = bikeService.findBikeById(rentRequest.getBikeId());
        User user = userService.findUserById(rentRequest.getUserId());
        ChargingStation chargingStation = chargingStationService.findStationById(bike.getChargingStation().getId());

        bike.setRented(true);
        bike.setChargingStation(null);
        bike.setUser(user); //it is a relation @OneToOne therefore setting should be on both sides of owning
        user.setBike(bike);
        if (chargingStation.getBikeList() != null && !chargingStation.getBikeList().isEmpty()) {
            chargingStation.getBikeList().remove(bike);
        }
        chargingStation.setFreeSlots(chargingStation.getFreeSlots() + 1);
        int daysOfRent = rentRequest.getDaysOfRent();

        Rent rent = new Rent(null, LocalDateTime.now(), null, daysOfRent, 0.0, bike, user,
                null);
        rentRepository.save(rent);

        return new RentResponseDTO(
                rent.getId(),
                rent.getRentalStartTime(),
                null,
                rent.getDaysOfRent(),
                modelMapper.map(rent, BikeForNormalUserResponseDTO.class),
                modelMapper.map(rent, UserForNormalUserResponseDTO.class), null);
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnVehicle(Long rentId, Long returnChargingStationId) {
        Rent rent = findRentById(rentId);
        Bike bike = rent.getBike();
        User user = rent.getUser();
        ChargingStation returnChargingStation = chargingStationService.findStationById(returnChargingStationId);

        double rentalCost = countRentalCost(rentId);
        rent.setRentalEndTime(LocalDateTime.now());
        rent.setAmountToBePaid(rentalCost);
        rent.setChargingStation(returnChargingStation);

        bike.setRented(false);
        bike.setUser(null);

        returnChargingStation.getBikeList().add(bike); //Bike is an owning side, so I must add below line to save it in db
        bike.setChargingStation(returnChargingStation);

        returnChargingStation.setFreeSlots(returnChargingStation.getFreeSlots() - 1);

        user.setBalance(user.getBalance() - rentalCost); //reducing the user balance by rental cost

        bikeService.save(bike);
        chargingStationService.save(returnChargingStation);
        userService.save(user);
        rentRepository.save(rent);

        return new ResponseEntity<>("Bike successfully returned.", HttpStatus.OK);
    }

    private double countRentalCost(Long rentId) {
        Rent rent = findRentById(rentId);
        int rentalDays = rent.getDaysOfRent();

        //option 1: if user chooses rent option for days
        if(rentalDays >0) {
            return rentalDays * 10;
        }

        //option 2: if user decides to rent per minutes
        long durationInMinutes = Duration.between(rent.getRentalStartTime(), rent.getRentalEndTime()).toMinutes();
        return durationInMinutes * 0.1;
    }

    private Rent findRentById(Long id) {
        return rentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
}
