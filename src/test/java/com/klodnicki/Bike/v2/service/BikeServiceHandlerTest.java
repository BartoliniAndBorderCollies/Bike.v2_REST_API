package com.klodnicki.Bike.v2.service;


import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BikeServiceHandlerTest {

    private BikeRepository bikeRepository;
    private ChargingStationRepository chargingStationRepository;
    private UserRepository userRepository;
    private BikeServiceHandler bikeServiceHandler;
    private ModelMapper modelMapper;
    private Bike bike;
    private BikeRequestDTO bikeRequestDTO;
    private BikeForAdminResponseDTO bikeForAdminResponseDTO;

    @BeforeEach
    public void setUp(){
        bikeRepository = mock(BikeRepository.class);
        chargingStationRepository = mock(ChargingStationRepository.class);
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        bikeServiceHandler = new BikeServiceHandler(bikeRepository, modelMapper);
        bike = mock(Bike.class);
        bikeRequestDTO = mock(BikeRequestDTO.class);
        bikeForAdminResponseDTO = mock(BikeForAdminResponseDTO.class);
    }

    @Test
    public void add_ShouldCallOnRepositoryExactlyOnce_WhenGivenBikeRequestDTOObject() {
        //Arrange
        when(modelMapper.map(bikeRequestDTO, Bike.class)).thenReturn(bike);
        when(bikeRepository.save(bike)).thenReturn(bike);
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        //Act
        bikeServiceHandler.add(bikeRequestDTO);

        //Assert
        verify(bikeRepository, times(1)).save(bike);
    }

    @Test
    public void add_ShouldReturnBikeForAdminResponseDTO_WhenGivenBikeRequestDTO() {
        //Arrange
        when(modelMapper.map(bikeRequestDTO, Bike.class)).thenReturn(bike);
        when(bikeRepository.save(bike)).thenReturn(bike);
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        //Act
        BikeForAdminResponseDTO actual = bikeServiceHandler.add(bikeRequestDTO);

        //Assert
        assertEquals(bikeForAdminResponseDTO, actual);
    }

    @Test
    public void findAll_ShouldReturnListOfBikeForAdminResponseDTO_WhenBikeExistInDatabase() {
        //given
        bikeRepository.deleteAll();
        ArrayList<BikeForAdminResponseDTO> list = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Bike bike = new Bike();
            bikeRepository.save(bike);
            BikeForAdminResponseDTO bikeDTO = modelMapper.map(bike, BikeForAdminResponseDTO.class);
            list.add(bikeDTO);
        }

        //when
        List<BikeForAdminResponseDTO> actual = bikeServiceHandler.findAll();

        //then
        assertEquals(list, actual);
    }

    @Test
    public void findById_ShouldReturnBikeForAdminResponseDTO_WhenBikeExistsInDatabase() {
        //given
        bikeRepository.deleteAll();
        Bike bike = new Bike();
        bikeRepository.save(bike);

        BikeForAdminResponseDTO expected = modelMapper.map(bike, BikeForAdminResponseDTO.class);

        //when
        BikeForAdminResponseDTO actual = bikeServiceHandler.findById(bike.getId());

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_ShouldReturnUpdatedBikeForAdminResponseDTO_WhenGivenCorrectIdAndBikeRequestDTO() {
        //given
        bikeRepository.deleteAll();
        Bike bike = new Bike();
        bike.setRent(new Rent());

        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);

        User user = new User();
        userRepository.save(user);

        bike.setId(bike.getId());
        bike.setSerialNumber("123");
        bike.setRented(true);
        bike.setBikeType(BikeType.ELECTRIC);
        bike.getRent().setAmountToBePaid(100.00);
        bike.setGpsCoordinates(new GpsCoordinates("50N", "40E"));
        bike.setUser(user);
        bike.setChargingStation(chargingStation);

        bikeRepository.save(bike);

        BikeForAdminResponseDTO expected = modelMapper.map(bike, BikeForAdminResponseDTO.class);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(bike.getId(), "123", true, BikeType.ELECTRIC,
                100.00, new GpsCoordinates("50N", "40E"), new UserForAdminResponseDTO(),
                new StationForAdminResponseDTO());

        //when
        BikeForAdminResponseDTO actual = bikeServiceHandler.update(bike.getId(), bikeRequestDTO);

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void update_ShouldReturnEmptyOptional_WhenBikeRequestDTOValuesAreNulls() {
        //given
        bikeRepository.deleteAll();
        Bike bike = new Bike();
        bikeRepository.save(bike);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(bike.getId(), null, true, null,
                100.00, null, new UserForAdminResponseDTO(), new StationForAdminResponseDTO());

        //when
        BikeForAdminResponseDTO actual = bikeServiceHandler.update(bike.getId(), bikeRequestDTO);

        //then
        assertNull(actual.getSerialNumber());
        assertNull(actual.getBikeType());
        assertNull(actual.getGpsCoordinates());
    }

    @Test
    public void deleteById_ShouldDeleteFromDatabase_WhenGivenId() {
        //given
        bikeRepository.deleteAll();
        Bike bike = new Bike();
        bikeRepository.save(bike);
        Long expected = bikeRepository.count() - 1;

        //when
        bikeServiceHandler.deleteById(bike.getId());

        //then
        assertEquals(expected, bikeRepository.count());
    }

    @Test
    public void findByIsRentedFalse_ShouldReturnListOfNotRentedBikes_WhenRentIsFalse() {
        //given
        bikeRepository.deleteAll();
        List<Bike> expected = new ArrayList<>();

        Bike bike = new Bike();
        Bike bike2 = new Bike();
        Bike bike3 = new Bike();

        bike.setRented(true);
        bike3.setRented(true);

        bikeRepository.save(bike);
        bikeRepository.save(bike2);
        bikeRepository.save(bike3);

        expected.add(bike2);

        //when
        List<Bike> actual = bikeServiceHandler.findByIsRentedFalse();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void save_ShouldSaveInDatabase_WhenGivenBikeObject() {
        //given
        bikeRepository.deleteAll();
        Long expected = bikeRepository.count() + 1;
        Bike bike = new Bike();

        //when
        bikeServiceHandler.save(bike);
        Long actual = bikeRepository.count();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void findBikeById_ShouldThrowIllegalArgumentException_WhenGivenIdDoesNotExistInDatabase() {
        //given
        bikeRepository.deleteAll();

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> bikeServiceHandler.findBikeById(1L));
    }
}