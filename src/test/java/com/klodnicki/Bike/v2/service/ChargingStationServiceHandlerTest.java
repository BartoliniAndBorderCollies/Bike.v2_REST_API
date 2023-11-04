package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChargingStationServiceHandlerTest {

    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ChargingStationServiceHandler chargingStationServiceHandler;
    @Autowired
    private ModelMapper modelMapper;

    @BeforeEach
    public void cleanDatabase() {
        chargingStationRepository.deleteAll();
    }

    @Test
    public void add_ShouldAddToDatabase_WhenGivenCorrectArguments() {
        //given
        Long expected = chargingStationRepository.count() + 1;
        ChargingStation chargingStation = new ChargingStation();

        //when
        chargingStationServiceHandler.add(chargingStation);
        Long actual = chargingStationRepository.count();

        //then
        assertEquals(expected, actual);
    }

    @Test
    public void add_ShouldReturnStationForAdminResponseDTO_WhenGivenChargingStation() {
        //given
        ChargingStation chargingStation = new ChargingStation();
        StationForAdminResponseDTO expected = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        //when
        StationForAdminResponseDTO actual = chargingStationServiceHandler.add(chargingStation);

        //then
        assertNotNull(actual.getId());
        expected.setId(actual.getId());
        assertEquals(expected, actual);
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
        chargingStationRepository.save(chargingStation);

        //when
        ChargingStation actual = chargingStationServiceHandler.findStationById(chargingStation.getId());
        actual.setBikeList(null);

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