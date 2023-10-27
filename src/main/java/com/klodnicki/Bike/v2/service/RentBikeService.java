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
import com.klodnicki.Bike.v2.service.interfacee.*;
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
public class RentBikeService implements GenericRentBikeService {

    private final GenericBikeService bikeService;
    private final GenericChargingStationService chargingStationService;
    private final GenericUserService userService;
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
        User user = findUserById(rentRequest.getUserId());
        ChargingStation chargingStation = findStationById(rentRequest.getChargingStationId());

        bike.setRented(true);
        bike.setChargingStation(null);
        bike.setAmountToBePaid(0.0);
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
    public ChargingStation addBikeToList(Long chargingStationId, Bike bike) {
        ChargingStation chargingStation = findStationById(chargingStationId);
        chargingStation.getBikeList().add(bike);
        //In JPA, only the owning side of the relationship is used when writing to the database.
        //which means this will not be saved in database. Charging station still will have an empty bike list.
        //because ChargingStation is NOT the owning-side. Therefore, I must add line below to fix this problem
        bike.setChargingStation(chargingStation);

        return chargingStationService.save(chargingStation);
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnVehicle(Long rentId, Long returnChargingStationId, Long bikeId) {
        Bike bike = bikeService.findBikeById(bikeId);
        ChargingStation returnChargingStation = findStationById(returnChargingStationId);
        Rent rent = findRentById(rentId);
        User user = findUserById(bike.getUser().getId());

        rent.setRentalEndTime(LocalDateTime.now());

        bike.setRented(false);
        bike.setAmountToBePaid(countRentalCost(rentId));
        bike.setUser(null);

        returnChargingStation.getBikeList().add(bike); //Bike is an owning side, so I must add below line to save it in db
        bike.setChargingStation(returnChargingStation);

        returnChargingStation.setFreeSlots(returnChargingStation.getFreeSlots() - 1);

        user.setBalance(user.getBalance() - countRentalCost(rentId)); //reducing the user balance by rental cost

        rent.setChargingStation(returnChargingStation);

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

    private User findUserById(Long id) {
        return userService.findUserById(id);
    }

    private ChargingStation findStationById(Long returnChargingStationId) {
        return chargingStationService.findStationById(returnChargingStationId);
    }
}
