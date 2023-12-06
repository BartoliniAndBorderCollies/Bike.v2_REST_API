package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class ChargingStationServiceHandlerTest {

    private ChargingStationRepository chargingStationRepository;
    private BikeRepository bikeRepository;
    private ChargingStationServiceHandler chargingStationServiceHandler;
    private BikeServiceHandler bikeServiceHandler;
    private ModelMapper modelMapper;
    private ChargingStation chargingStation;

    @BeforeEach
    public void setUp() {
        chargingStationRepository = mock(ChargingStationRepository.class);
        bikeRepository = mock(BikeRepository.class);
        bikeServiceHandler = mock(BikeServiceHandler.class);
        modelMapper = mock(ModelMapper.class);
        chargingStationServiceHandler = new ChargingStationServiceHandler(chargingStationRepository, bikeServiceHandler,
                modelMapper);
        chargingStation = mock(ChargingStation.class);

        when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);
    }

    @Test
    public void add_ShouldCallOnChargingStationRepositoryExactlyOnce_WhenChargingStationProvided() {
        //Arrange - takes from @BeforeEach setUp()
        //Act
        chargingStationServiceHandler.add(chargingStation);

        //Assert
        verify(chargingStationRepository, times(1)).save(chargingStation);
    }

    @Test
    public void add_ShouldReturnStationForAdminResponseDTO_WhenGivenChargingStation() {
        //Arrange - takes from @BeforeEach setUp()
        StationForAdminResponseDTO stationDTO = new StationForAdminResponseDTO();
        when(modelMapper.map(chargingStation, StationForAdminResponseDTO.class)).thenReturn(stationDTO);

        //Act
        StationForAdminResponseDTO actual = chargingStationServiceHandler.add(chargingStation);

        //Assert
        assertEquals(stationDTO, actual);
    }

    @Test
    public void findAll_ShouldReturnListOfStationForAdminResponseDTO_WhenBikesExistInDatabase() {
        //Arrange - takes from @BeforeEach setUp()
        List<ChargingStation> stations = new ArrayList<>();
        List<StationForAdminResponseDTO> expectedStationDTOS = new ArrayList<>();
        StationForAdminResponseDTO stationDTO = new StationForAdminResponseDTO();
        when(chargingStationRepository.findAll()).thenReturn(stations);
        when(modelMapper.map(chargingStation, StationForAdminResponseDTO.class)).thenReturn(stationDTO);

        for (int i = 0; i < 5; i++) {
            expectedStationDTOS.add(stationDTO);
            stations.add(chargingStation);
        }

        //Act
        List<StationForAdminResponseDTO> actual = chargingStationServiceHandler.findAll();

        //Assert
        assertEquals(expectedStationDTOS, actual);
    }

    @Test
    public void findById_ShouldReturnStationForAdminResponseDTO_WhenExistInDatabase() {
        //Arrange
        when(chargingStationRepository.findById(chargingStation.getId())).thenReturn(Optional.of(chargingStation));
        StationForAdminResponseDTO stationDTO = new StationForAdminResponseDTO();
        when(modelMapper.map(chargingStation, StationForAdminResponseDTO.class)).thenReturn(stationDTO);

        //Act
        StationForAdminResponseDTO actual = chargingStationServiceHandler.findById(chargingStation.getId());

        //Assert
        assertEquals(stationDTO, actual);
    }

    @Test
    public void findStationById_ShouldReturnChargingStation_WhenExistInDatabase() throws NotFoundInDatabaseException {
        //Arrange
        when(chargingStationRepository.findById(chargingStation.getId())).thenReturn(Optional.of(chargingStation));

        //Act
        ChargingStation actual = chargingStationServiceHandler.findStationById(chargingStation.getId());

        //Assert
        assertEquals(chargingStation, actual);
    }

    @Test
    public void findStationById_ShouldThrowNotFoundInDbException_WhenNotFoundChargingStationInDatabase() {
        assertThrows(NotFoundInDatabaseException.class, () -> chargingStationServiceHandler.findStationById(1L));
    }


    @Test
    public void save_ShouldCallOnChargingStationRepositoryExactlyOnce_WhenChargingStationIsProvided() {
        //Arrange - takes from @BeforeEach setUp()
        //Act
        chargingStationServiceHandler.save(chargingStation);

        //Assert
        verify(chargingStationRepository, times(1)).save(chargingStation);
    }

    @Test
    public void addBikeToList_ShouldAddBikeToList_WhenIdsOfBikeAndStationAreProvided() throws NotFoundInDatabaseException {
        //Arrange
        List<Bike> bikeList = new ArrayList<>();
        Bike bike = new Bike();
        bikeList.add(bike);

        chargingStation = new ChargingStation();

        when(chargingStationRepository.findById(chargingStation.getId())).thenReturn(Optional.of(chargingStation));
        when(bikeServiceHandler.findBikeById(bike.getId())).thenReturn(bike);
        when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);

        bike.setChargingStation(chargingStation);
        chargingStation.setBikeList(bikeList);

        //Act
        List<Bike> actual = chargingStationServiceHandler.addBikeToList(chargingStation.getId(), bike.getId()).getBikeList();

        //Assert
        assertIterableEquals(bikeList, actual);
    }
}