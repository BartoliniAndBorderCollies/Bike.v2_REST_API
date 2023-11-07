package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import com.klodnicki.Bike.v2.service.api.ChargingStationServiceApi;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RentBikeServiceHandlerTest {

    private RentRepository rentRepository;
    private BikeRepository bikeRepository;
    private ChargingStationRepository chargingStationRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private RentBikeServiceHandler rentBikeServiceHandler;
    private BikeServiceHandler bikeServiceHandler;
    private ChargingStationServiceApi chargingStationService;
    private UserServiceApi userService;

    @BeforeEach
    public void setUp() {
        rentRepository = mock(RentRepository.class);
        bikeRepository = mock(BikeRepository.class);
        chargingStationRepository = mock(ChargingStationRepository.class);
        userRepository = mock(UserRepository.class);

        modelMapper = new ModelMapper();
        bikeServiceHandler = new BikeServiceHandler(bikeRepository, modelMapper);
        userService = new UserServiceHandler(userRepository, modelMapper);
        chargingStationService = new ChargingStationServiceHandler(chargingStationRepository, bikeServiceHandler, modelMapper);
        rentBikeServiceHandler = new RentBikeServiceHandler(bikeServiceHandler, chargingStationService, userService, modelMapper, rentRepository);
    }

    @Test
    public void updateRent_ShouldReturnUpdatedDaysOfRentResponseDTO_WhenGivenCorrectArguments() {
        //Arrange
        Rent rent = new Rent();
        rent.setId(1L);

        when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));

        RentRequestDTO rentRequestDTO = new RentRequestDTO();
        rentRequestDTO.setId(1L);
        rentRequestDTO.setDaysOfRent(10);

        RentResponseDTO expected = modelMapper.map(rentRequestDTO, RentResponseDTO.class);

        //Act
        RentResponseDTO actual = rentBikeServiceHandler.updateRent(rent.getId(), rentRequestDTO);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void findAvailableBikes_ShouldReturnListOfBikeForNormalUserResponseDTO_WhenGivenListOfBikes() {
        //Arrange
        List<Bike> listOfBikes = new ArrayList<>();
        List<BikeForNormalUserResponseDTO> expected = new ArrayList<>();

        when(bikeRepository.findByIsRentedFalse()).thenReturn(listOfBikes);

        for (int i = 0; i < 3; i++) {
            Bike bike = new Bike();
            bike.setRented(false);
            listOfBikes.add(bike);

            expected.add(modelMapper.map(bike, BikeForNormalUserResponseDTO.class));
        }

        //Act
        List<BikeForNormalUserResponseDTO> actual = rentBikeServiceHandler.findAvailableBikes();

        //Assert
        assertIterableEquals(expected, actual);
    }
}