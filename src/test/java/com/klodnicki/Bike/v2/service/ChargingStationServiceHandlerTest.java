package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
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

    @Test
    public void add_ShouldAddToDatabase_WhenGivenCorrectArguments() {
        //given
        chargingStationRepository.deleteAll();
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
        chargingStationRepository.deleteAll();
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
        chargingStationRepository.deleteAll();
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
        chargingStationRepository.deleteAll();
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
        chargingStationRepository.deleteAll();
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);

        //when
        ChargingStation actual = chargingStationServiceHandler.findStationById(chargingStation.getId());
        actual.setBikeList(null);

        //then
        assertEquals(chargingStation, actual);
    }
}