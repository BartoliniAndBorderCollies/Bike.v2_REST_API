package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RentBikeService implements RentBikeGenericService{

    private final BikeRepository bikeRepository;
    private final RentRepository rentRepository;
    private final UserRepository userRepository;
    private final ChargingStationRepository chargingStationRepository;
    private final GenericBikeService bikeService;
    private final UserService userService;
    private final ChargingStationService chargingStationService;
    ModelMapper modelMapper = new ModelMapper();


    public RentBikeService(BikeRepository bikeRepository, RentRepository rentRepository,
                           UserRepository userRepository, GenericBikeService bikeService, UserService userService, ChargingStationService chargingStationService,
                           ChargingStationRepository chargingStationRepository) {
        this.bikeRepository = bikeRepository;
        this.rentRepository = rentRepository;
        this.userRepository = userRepository;
        this.bikeService = bikeService;
        this.userService = userService;
        this.chargingStationService = chargingStationService;
        this.chargingStationRepository = chargingStationRepository;
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        List<Bike> availableBikes = bikeRepository.findByIsRentedFalse();
        List<BikeForNormalUserResponseDTO> bikesForNormalUserDTO = new ArrayList<>();

        for (Bike bike: availableBikes) {

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
        User user = userService.findById(rentRequest.getUserId());
        ChargingStation chargingStation = chargingStationService.findById(rentRequest.getChargingStationId());

        bike.setRented(true);
        bike.setChargingStation(null);
        bike.setAmountToBePaid(0.0);
        bike.setUser(user); //it is a relation @OneToOne therefore setting should be on both sides of owning
        user.setBike(bike);
        if(chargingStation.getBikeList() != null && !chargingStation.getBikeList().isEmpty()) {
            chargingStation.getBikeList().remove(bike);
        }
        chargingStation.setFreeSlots(chargingStation.getFreeSlots()+1);
        int daysOfRent = rentRequest.getDaysOfRent();

        Rent rent = new Rent(LocalDateTime.now(), null, bike, user, null, daysOfRent);
        rentRepository.save(rent);

        return modelMapper.map(rent, RentResponseDTO.class);
    }

    @Override
    public ChargingStation addBikeToList(Long chargingStationId, Bike bike) {
        ChargingStation chargingStation = chargingStationService.findById(chargingStationId);
        chargingStation.getBikeList().add(bike);
        //In JPA, only the owning side of the relationship is used when writing to the database.
        //which means this will not be saved in database. Charging station still will have an empty bike list.
        //because ChargingStation is NOT the owning-side. Therefore, I must add line below to fix this problem
        bike.setChargingStation(chargingStation);

        return chargingStationRepository.save(chargingStation);
    }

    @Override
    public Rent updateRent(Long id, Rent rentToBeUpdated) {
        Rent rent = getRent(id);

        if(rentToBeUpdated.getDaysOfRent() != 0) {
            rent.setDaysOfRent(rentToBeUpdated.getDaysOfRent());
        }
        return rentRepository.save(rent);
    }

    private Rent getRent(Long id) {
        return rentRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    @Override
    @Transactional
    public void returnBike(Long rentId, Long returnChargingStationId, Long bikeId) {
        Bike bike = bikeService.getBike(bikeId);
        ChargingStation returnChargingStation = chargingStationService.findById(returnChargingStationId);
        Rent rent = getRent(rentId);
        User user = userService.findById(bike.getUser().getId());

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

        returnChargingStation.setFreeSlots(returnChargingStation.getFreeSlots()-1);

        bikeRepository.save(bike);
        chargingStationRepository.save(returnChargingStation);
        userRepository.save(user);
        rentRepository.save(rent);

        rentRepository.deleteById(rentId);
    }

    private double countRentalCost(Long rentId) {
        Rent rent = getRent(rentId);
        int rentalDays = rent.getDaysOfRent();
        return rentalDays * 10;
    }
}
