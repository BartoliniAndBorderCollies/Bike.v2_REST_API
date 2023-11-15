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
import java.util.Optional;

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
        //Arrange
        List<Bike> bikeList = new ArrayList<>();
        List<BikeForAdminResponseDTO> bikeListDto = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            bikeList.add(bike);
            bikeListDto.add(bikeForAdminResponseDTO);
        }

        when(bikeRepository.findAll()).thenReturn(bikeList);
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        //Act
        List<BikeForAdminResponseDTO> actual = bikeServiceHandler.findAll();

        //Assert
        assertEquals(bikeListDto, actual);
    }

    @Test
    public void findById_ShouldReturnBikeForAdminResponseDTO_WhenBikeExistsInDatabaseAndIdOfBikeIsProvided() {
        //Arrange
        when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        //Act
        BikeForAdminResponseDTO actual = bikeServiceHandler.findById(bike.getId());

        //then
        assertEquals(bikeForAdminResponseDTO, actual);
    }

    @Test
    public void update_ShouldReturnUpdatedBikeForAdminResponseDTO_WhenGivenCorrectIdAndBikeRequestDTO() {
        //Arrange
        bike = new Bike();
        bike.setRent(new Rent());

        ChargingStation chargingStation = mock(ChargingStation.class);
        User user = mock(User.class);
        UserForAdminResponseDTO userDTO = mock(UserForAdminResponseDTO.class);
        GpsCoordinates gpsCoordinates = mock(GpsCoordinates.class);
        StationForAdminResponseDTO stationDTO = mock(StationForAdminResponseDTO.class);

        when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));
        when(bikeRepository.save(bike)).thenReturn(bike);
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        bike.setId(bike.getId());
        bike.setSerialNumber("123");
        bike.setRented(true);
        bike.setBikeType(BikeType.ELECTRIC);
        bike.getRent().setAmountToBePaid(100.00);
        bike.setGpsCoordinates(gpsCoordinates);
        bike.setUser(user);
        bike.setChargingStation(chargingStation);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(bike.getId(), "123", true, BikeType.ELECTRIC,
                100.00, gpsCoordinates, userDTO, stationDTO);

        //Act
        BikeForAdminResponseDTO actual = bikeServiceHandler.update(bike.getId(), bikeRequestDTO);

        //Assert
        assertEquals(bikeForAdminResponseDTO, actual);
    }

    @Test
    public void update_ShouldReturnEmptyOptional_WhenBikeRequestDTOValuesAreNulls() {
        //Arrange
        Bike bike = mock(Bike.class);
        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(null, null, true, null,
                100.00, null, new UserForAdminResponseDTO(), new StationForAdminResponseDTO());

        when(bikeRepository.findById(bike.getId())).thenReturn(Optional.of(bike));
        when(bikeRepository.save(bike)).thenReturn(bike);
        when(modelMapper.map(bike, BikeForAdminResponseDTO.class)).thenReturn(bikeForAdminResponseDTO);

        //Act
        BikeForAdminResponseDTO actual = bikeServiceHandler.update(bike.getId(), bikeRequestDTO);

        //Assert
        assertNull(actual.getSerialNumber());
        assertNull(actual.getBikeType());
        assertNull(actual.getGpsCoordinates());
    }

    @Test
    public void deleteById_ShouldCallOnRepositoryExactlyOnce_WhenGivenId() {
        //Arrange
        //Act
        bikeServiceHandler.deleteById(bike.getId());

        //Assert
        verify(bikeRepository, times(1)).deleteById(bike.getId());
    }

    @Test
    public void findByIsRentedFalse_ShouldReturnListOfNotRentedBikes_WhenNotRentedBikesAreInDatabase() {
        //Arrange
        List<Bike> expected = new ArrayList<>();

        Bike bike1 = new Bike();
        bike1.setRented(true);
        Bike bike2 = new Bike();
        bike2.setRented(false);
        Bike bike3 = new Bike();
        bike3.setRented(true);

        expected.add(bike2);

        when(bikeRepository.findByIsRentedFalse()).thenReturn(expected);

        //Act
        List<Bike> actual = bikeServiceHandler.findByIsRentedFalse();

        //Assert
        assertIterableEquals(expected, actual);
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