package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import com.klodnicki.Bike.v2.service.api.ChargingStationServiceApi;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    private ChargingStation chargingStation;
    private Bike bike;

    @BeforeEach
    public void setUp() {
        rentRepository = mock(RentRepository.class);
        bikeRepository = mock(BikeRepository.class);
        chargingStationRepository = mock(ChargingStationRepository.class);
        userRepository = mock(UserRepository.class);

        modelMapper = mock(ModelMapper.class);
        bikeServiceHandler = mock(BikeServiceHandler.class);
        userService = mock(UserServiceHandler.class);
        chargingStationService = mock(ChargingStationServiceHandler.class);
        rentBikeServiceHandler = new RentBikeServiceHandler(bikeServiceHandler, chargingStationService, userService,
                modelMapper, rentRepository);

        bike = mock(Bike.class);
    }

    @Test
    public void updateRent_ShouldReturnUpdatedDaysOfRentResponseDTO_WhenGivenCorrectArguments() {
        //Arrange
        Rent rent = mock(Rent.class);

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
       List<Bike> bikeList = new ArrayList<>();
        List<BikeForNormalUserResponseDTO> expected = new ArrayList<>();

        when(bikeServiceHandler.findByIsRentedFalse()).thenReturn(bikeList);

        for (int i = 0; i < 3; i++) {
            Bike bike = new Bike();
            bike.setRented(false);
            bikeList.add(bike);

            BikeForNormalUserResponseDTO bikeDto = new BikeForNormalUserResponseDTO();
            expected.add(bikeDto);

            when(modelMapper.map(bike, BikeForNormalUserResponseDTO.class)).thenReturn(bikeDto);
        }

        //Act
        List<BikeForNormalUserResponseDTO> actual = rentBikeServiceHandler.findAvailableBikes();

        //Assert
        assertIterableEquals(expected, actual);
    }

    @Test
    public void findBikeForNormalUserById_ShouldReturnBikeForNormalUserResponseDTO_WhenGivenCorrectId() {
        //Arrange
        when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));

        BikeForNormalUserResponseDTO expected = modelMapper.map(bike, BikeForNormalUserResponseDTO.class);

        //Act
        BikeForNormalUserResponseDTO actual = rentBikeServiceHandler.findBikeForNormalUserById(bike.getId());

        //Assert
        assertEquals(expected, actual);
    }

    @Nested
    class nestedTestsForRentMethods {

        private RentRequest rentRequest;
        private User user;

        @BeforeEach
        public void setUpForRentMethods() throws NotFoundInDatabaseException {

            List<Bike> bikeList = new ArrayList<>();

            bike = new Bike();
            bike.setId(1L);
            bikeList.add(bike);

            user = mock(User.class);

            chargingStation = new ChargingStation();
            chargingStation.setId(1L);
            chargingStation.setBikeList(bikeList);

            rentRequest = new RentRequest();
            rentRequest.setId(1L);
            rentRequest.setBikeId(bike.getId());
            rentRequest.setUserId(user.getId());
            rentRequest.setDaysOfRent(10);

            bike.setChargingStation(chargingStation);

            when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));
            when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
            when(chargingStationRepository.findById(chargingStation.getId())).thenReturn(Optional.of(chargingStation));

            when(bikeServiceHandler.findBikeById(rentRequest.getBikeId())).thenReturn(bike);
            when(userService.findUserById(rentRequest.getUserId())).thenReturn(user);
            when(chargingStationService.findStationById(bike.getChargingStation().getId())).thenReturn(chargingStation);
        }

        @Test
        public void rent_ShouldRemoveFromBikeList_WhenProvidedBikeObject() throws NotFoundInDatabaseException {
            //Arrange
            //Act
            rentBikeServiceHandler.rent(rentRequest);
            List<Bike> actual = chargingStation.getBikeList();

            //Assert
            assertTrue(actual.isEmpty());
        }

        @Test
        public void rent_ShouldIncrementFreeSlotsByOne_WhenBikeIsRented() throws NotFoundInDatabaseException {
            //Arrange
            chargingStation.setFreeSlots(1);
            int expected = chargingStation.getFreeSlots() + 1;

            //Act
            rentBikeServiceHandler.rent(rentRequest);
            int actual = chargingStation.getFreeSlots();

            //Assert
            assertEquals(expected, actual);
        }

        @Test
        public void rent_ShouldCallOnRentRepositoryExactlyOnceWithCorrectState_WhenRentProvided() throws NotFoundInDatabaseException {
            //Arrange
            //Act
            rentBikeServiceHandler.rent(rentRequest);

            //Assert
            ArgumentCaptor<Rent> rentCaptor = ArgumentCaptor.forClass(Rent.class); //I capture a Rent object which is passed
            // to save(), because rent in test method and rent in original method are two different instances

            verify(rentRepository, times(1)).save(rentCaptor.capture()); //I verify if the save method
            // of rentRepository is called exactly once. The rentCaptor.capture() I use as the argument to save,
            // this tells Mockito to capture the argument that is passed in when save is called.

            Rent capturedRent = rentCaptor.getValue(); //I get the captured value

            assertEquals(bike, capturedRent.getBike()); // I check if these objects are equals to the objects which I set up
            // earlier in my test method
            assertEquals(user, capturedRent.getUser());
            assertEquals(rentRequest.getDaysOfRent(), capturedRent.getDaysOfRent());

            //So this code captures Rent object that is passed to rentRepository.save(), and then it checks if its fields
            // have the expected values. So that I not only check if it was called once, but also if arguments
            // themselves have the correct state.
        }
    }

    @Nested
    class nestedTestsForReturnVehicleMethods {

        private Rent rent;
        private List<Bike> bikeList;

        @BeforeEach
        public void setUpForReturnVehicleMethod() throws NotFoundInDatabaseException {
            bikeList = new ArrayList<>();

            rent = mock(Rent.class);
            User user = mock(User.class);
            chargingStation = new ChargingStation();
            chargingStation.setId(1L);
            chargingStation.setBikeList(bikeList);

            when(rentRepository.findById(rent.getId())).thenReturn(Optional.of(rent));
            when(rent.getBike()).thenReturn(bike); //I should create mocks so that I don't have to set up these instances,
            // Mockito will return the mock objects, and then I can define their behaviour by thenReturn
            when(chargingStationService.findStationById(chargingStation.getId())).thenReturn(chargingStation);
            when(rent.getUser()).thenReturn(user);
            when(chargingStationRepository.findById(chargingStation.getId())).thenReturn(Optional.of(chargingStation));
            when(rent.getRentalStartTime()).thenReturn(LocalDateTime.of(2023, 1, 1, 0, 0));
            when(rent.getRentalEndTime()).thenReturn(LocalDateTime.of(2023, 1, 1, 1, 0));
        }

        @Test
        public void returnVehicle_ShouldAddReturnedBikeToStation_WhenGivenBikeObject() throws NotFoundInDatabaseException {
            //Arrange
            bikeList.add(bike);

            //Act
            rentBikeServiceHandler.returnVehicle(rent.getId(), chargingStation.getId());
            List<Bike> actual = chargingStation.getBikeList();

            //Assert
            assertIterableEquals(bikeList, actual);
        }

        @Test
        public void returnVehicle_ShouldDecrementStationFreeSlotsByOne_WhenBikeIsReturned() throws NotFoundInDatabaseException {
            //Arrange
            chargingStation.setFreeSlots(1); //should decrement by one

            //Act
            rentBikeServiceHandler.returnVehicle(rent.getId(), chargingStation.getId());
            int actual = chargingStation.getFreeSlots();

            //Assert
            assertEquals(0, actual);
        }
    }
}