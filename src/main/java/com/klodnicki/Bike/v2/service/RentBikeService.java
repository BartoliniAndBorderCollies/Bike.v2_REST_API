package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
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

    private final GenericBikeService bikeService;
    private final UserService userService;
    private final ChargingStationService chargingStationService;


    ModelMapper modelMapper = new ModelMapper();
    private final UserRepository userRepository;
    private final ChargingStationRepository chargingStationRepository;

    public RentBikeService(BikeRepository bikeRepository, RentRepository rentRepository,
                           GenericBikeService bikeService, UserService userService, ChargingStationService chargingStationService, UserRepository userRepository,
                           ChargingStationRepository chargingStationRepository) {
        this.bikeRepository = bikeRepository;
        this.rentRepository = rentRepository;
        this.bikeService = bikeService;
        this.userService = userService;
        this.chargingStationService = chargingStationService;
        this.userRepository = userRepository;
        this.chargingStationRepository = chargingStationRepository;
    }


    @Override
    public BikeForNormalUserResponseDTO add(BikeRequestDTO obj) {
        return null;
    }

    @Override
    public BikeForNormalUserResponseDTO findById(Long id) {
        return null;
    }

    @Override
    public List<BikeForNormalUserResponseDTO> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public BikeForNormalUserResponseDTO update(Long id, BikeRequestDTO obj) {
        return null;
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
                    .rentalStartTime(bike.getRentalStartTime())
                    .rentalEndTime(bike.getRentalEndTime())
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
    public Rent rentBike(User user, Bike bike, ChargingStation chargingStation) {

        bike.setRented(true);
        bike.setRentalStartTime(LocalDateTime.now());
        bike.setChargingStation(null);
        bike.setUser(user); //it is a relation @OneToOne therefore setting should be on both sides of owning
        user.setBike(bike);
        if(chargingStation.getBikeList() != null && !chargingStation.getBikeList().isEmpty()) {
            chargingStation.getBikeList().remove(bike);
        }
        chargingStation.setFreeSlots(chargingStation.getFreeSlots()+1);

        Rent rent = new Rent(bike, user, chargingStation);

       return rentRepository.save(rent);
    }


}
