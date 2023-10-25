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
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentBikeService implements RentBikeGenericService {


    private final GenericBikeService bikeService;
    private final ChargingStationService chargingStationService;
    private final UserService userService;
    private final ModelMapper modelMapper;
    private final RentRepository rentRepository;


    public RentBikeService(GenericBikeService bikeService, ChargingStationService chargingStationService, UserService userService, ModelMapper modelMapper, RentRepository rentRepository) {
        this.bikeService = bikeService;
        this.chargingStationService = chargingStationService;
        this.userService = userService;
        this.modelMapper = modelMapper;
        this.rentRepository = rentRepository;
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        List<Bike> availableBikes = bikeRepository.findByIsRentedFalse();
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
        Bike bike = bikeRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return modelMapper.map(bike, BikeForNormalUserResponseDTO.class);
    }

    @Override
    @Transactional
    public RentResponseDTO rentBike(RentRequest rentRequest) {
        Bike bike = bikeService.getBike(rentRequest.getBikeId());
        User user = getUser(rentRequest.getUserId());
        ChargingStation chargingStation = getChargingStation(rentRequest.getChargingStationId());

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

        Rent rent = new Rent(LocalDateTime.now(), null, bike, user, null, daysOfRent);
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
        ChargingStation chargingStation = getChargingStation(chargingStationId);
        chargingStation.getBikeList().add(bike);
        //In JPA, only the owning side of the relationship is used when writing to the database.
        //which means this will not be saved in database. Charging station still will have an empty bike list.
        //because ChargingStation is NOT the owning-side. Therefore, I must add line below to fix this problem
        bike.setChargingStation(chargingStation);

        return chargingStationRepository.save(chargingStation);
    }

    @Override
    public RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO) {
        Rent rent = getRent(id);

        if (rentRequestDTO.getDaysOfRent() > 0) {
            rent.setDaysOfRent(rentRequestDTO.getDaysOfRent());
        }
        rentRepository.save(rent);
        return modelMapper.map(rent, RentResponseDTO.class);
    }

    @Override
    @Transactional
    public ResponseEntity<?> returnBike(Long rentId, Long returnChargingStationId, Long bikeId) {
        Bike bike = bikeService.getBike(bikeId);
        ChargingStation returnChargingStation = getChargingStation(returnChargingStationId);
        Rent rent = getRent(rentId);
        User user = getUser(rentId);

        rent.setBike(null); //I deleted cascades because all entities were gone together with Rent, so I set nulls,
        // save it in repo and then delete -> otherwise Rent record will not be deleted in db, because it holds FK.
        //I must do it on both sides because Rent is not-owning side of the relationship.
        bike.setRent(null);

        rent.setUser(null);
        user.setRent(null);

        bike.setRented(false);
        bike.setAmountToBePaid(countRentalCost(rentId));
        bike.setUser(null);

        returnChargingStation.getBikeList().add(bike); //Bike is an owning side, so I must add below line to save it in db
        bike.setChargingStation(returnChargingStation);

        returnChargingStation.setFreeSlots(returnChargingStation.getFreeSlots() - 1);

        user.setBalance(user.getBalance() - countRentalCost(rentId)); //reducing the user balance by rental cost

        bikeRepository.save(bike);
        chargingStationRepository.save(returnChargingStation);
        userRepository.save(user);
        rentRepository.save(rent);

        rentRepository.deleteById(rentId);

        if(rentRepository.findById(rentId).isEmpty()) {
            return new ResponseEntity<>("Bike successfully returned.", HttpStatus.OK);
        }

        return new ResponseEntity<>("Try again!", HttpStatus.I_AM_A_TEAPOT);
    }

    private double countRentalCost(Long rentId) {
        Rent rent = getRent(rentId);
        int rentalDays = rent.getDaysOfRent();
        return rentalDays * 10;
    }

    private Rent getRent(Long id) {
        return rentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    private ChargingStation getChargingStation(Long returnChargingStationId) {
        return chargingStationRepository.findById(returnChargingStationId).orElseThrow(IllegalArgumentException::new);
    }
}
