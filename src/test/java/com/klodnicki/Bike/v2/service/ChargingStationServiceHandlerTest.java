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
    public void findAll_ShouldReturnListOfStationForAdminResponseDTO_WhenExistInDatabase() {
        //given
        List<StationForAdminResponseDTO> expectedStationDTOS = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ChargingStation chargingStation = new ChargingStation();
            chargingStationRepository.save(chargingStation);
            StationForAdminResponseDTO stationDTO = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);
            expectedStationDTOS.add(stationDTO);
        }

        //when
        List<StationForAdminResponseDTO> actual = chargingStationServiceHandler.findAll();

        //then
        assertEquals(expectedStationDTOS, actual);
    }

    @Test
    public void findById_ShouldReturnStationForAdminResponseDTO_WhenExistInDatabase() {
        //given
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);
        StationForAdminResponseDTO expected = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        //when
        StationForAdminResponseDTO actual = chargingStationServiceHandler.findById(chargingStation.getId());

        //then
        assertEquals(expected, actual);
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

        ChargingStation chargingStation =  new ChargingStation();
        chargingStationRepository.save(chargingStation);

        //when
        List<Bike> actual = chargingStationServiceHandler.addBikeToList(chargingStation.getId(), bike.getId()).getBikeList();

        //then
        assertIterableEquals(bikeList, actual);
    }
}