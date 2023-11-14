package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
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
    }

    @Test
    public void add_ShouldCallOnChargingStationRepositoryExactlyOnce_WhenChargingStationProvided() {
        //Arrange
        when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);

        //Act
        chargingStationServiceHandler.add(chargingStation);

        //Assert
        verify(chargingStationRepository, times(1)).save(chargingStation);
    }

    @Test
    public void add_ShouldReturnStationForAdminResponseDTO_WhenGivenChargingStation() {
        //Arrange
        StationForAdminResponseDTO stationDTO = new StationForAdminResponseDTO();
        when(modelMapper.map(chargingStation, StationForAdminResponseDTO.class)).thenReturn(stationDTO);
        when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);

        //Act
        StationForAdminResponseDTO actual = chargingStationServiceHandler.add(chargingStation);

        //Assert
        assertEquals(stationDTO, actual);
    }

    @Test
    public void findAll_ShouldReturnListOfStationForAdminResponseDTO_WhenBikesExistInDatabase() {
        //Arrange
        List<ChargingStation> stations = new ArrayList<>();
        List<StationForAdminResponseDTO> expectedStationDTOS = new ArrayList<>();
        StationForAdminResponseDTO stationDTO = new StationForAdminResponseDTO();
        when(chargingStationRepository.findAll()).thenReturn(stations);

        for (int i = 0; i < 5; i++) {
            when(chargingStationRepository.save(chargingStation)).thenReturn(chargingStation);
            when(modelMapper.map(chargingStation, StationForAdminResponseDTO.class)).thenReturn(stationDTO);
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
    public void findStationById_ShouldReturnChargingStation_WhenExistInDatabase() {
        //given
        ChargingStation chargingStation = new ChargingStation();
        chargingStation.setBikeList(new ArrayList<>());
        chargingStationRepository.save(chargingStation);

        //when
        ChargingStation actual = chargingStationServiceHandler.findStationById(chargingStation.getId());

        //then
        assertEquals(chargingStation, actual);
    }

    @Test
    public void findStationById_ShouldThrowIllegalArgumentException_WhenNotFoundChargingStationInDatabase() {
        //given
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> chargingStationServiceHandler.findStationById(1L));
    }


    @Test
    public void save_ShouldSaveInDatabase_WhenProvidedChargingStationObject() {
        //given
        ChargingStation chargingStation = new ChargingStation();
        Long expected = chargingStationRepository.count() + 1;

        //when
        chargingStationServiceHandler.save(chargingStation);
        Long actual = chargingStationRepository.count();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void addBikeToList_ShouldAddBikeToList_WhenProvidedCorrectArguments() {
        //given
        Bike bike = new Bike();
        bikeRepository.save(bike);

        List<Bike> bikeList = new ArrayList<>();
        bikeList.add(bike);

        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);

        //when
        List<Bike> actual = chargingStationServiceHandler.addBikeToList(chargingStation.getId(), bike.getId()).getBikeList();

        //then
        assertIterableEquals(bikeList, actual);
    }
}