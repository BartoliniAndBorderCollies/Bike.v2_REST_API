package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.station.ListStationsForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class AdminChargingStationControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ModelMapper modelMapper;
    private ChargingStation chargingStation;
    private Bike bike;

    @BeforeEach
    public void setUp() {
        chargingStation = new ChargingStation(null, "station name", "station address", "station city",
                100, new ArrayList<>());
        chargingStationRepository.save(chargingStation);

        bike = new Bike(null, BikeType.ELECTRIC, null, null, null);
        bikeRepository.save(bike);
    }

    @AfterEach
    public void clearDatabase() {
        bikeRepository.deleteAll();
        chargingStationRepository.deleteAll();
    }

    @Test
    public void addBikeToList_ShouldAddBikeToStationListOfBikesAndReturnChargingStation_WhenStationIdAndBikeIdAreGiven() {
        List<Bike> bikeList = new ArrayList<>();
        bikeList.add(bike);
        chargingStation.setBikeList(bikeList);

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/admin/stations/list/add/" + chargingStation.getId())
                        .queryParam("bikeId", bike.getId())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(ChargingStation.class)
                .consumeWith(response -> {
                            ChargingStation station = response.getResponseBody();
                            assertNotNull(station);
                            assertFalse(station.getBikeList().isEmpty());
                            assertIterableEquals(chargingStation.getBikeList(), station.getBikeList());
                        }
                );
    }

    @Test
    public void add_ShouldAddChargingStationToDatabaseAndReturnStationForAdminResponseDTO_WhenChargingStationIsGiven() {
        StationForAdminResponseDTO expected = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        webTestClient.post()
                .uri("/api/admin/stations/add")
                .bodyValue(chargingStation)
                .exchange()
                .expectStatus().isOk()
                .expectBody(StationForAdminResponseDTO.class)
                .consumeWith(response -> {
                            StationForAdminResponseDTO stationDTO = response.getResponseBody();
                            Optional<ChargingStation> stationInDatabase = chargingStationRepository.findById(chargingStation.getId());
                            assertTrue(chargingStationRepository.count() > 0);
                            assertTrue(stationInDatabase.isPresent());
                            assertNotNull(stationDTO);
                            assertEquals(expected.getId(), stationDTO.getId());
                            assertEquals(expected.getName(), stationDTO.getName());
                            assertEquals(expected.getAddress(), stationDTO.getAddress());
                            assertEquals(expected.getCity(), stationDTO.getCity());
                            assertEquals(expected.getFreeSlots(), stationDTO.getFreeSlots());
                        }
                );
    }

    @Test
    public void findById_ShouldReturnStationForAdminResponseDTO_WhenStationIdIsGivenAndStationExistsInDatabase() {

        webTestClient.get()
                .uri("/api/admin/stations/" + chargingStation.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(StationForAdminResponseDTO.class)
                .consumeWith(response -> {
                    StationForAdminResponseDTO stationDTO = response.getResponseBody();
                    assertNotNull(stationDTO);
                    assertEquals(chargingStation.getId(), stationDTO.getId());
                    assertEquals(chargingStation.getName(), stationDTO.getName());
                    assertEquals(chargingStation.getAddress(), stationDTO.getAddress());
                    assertEquals(chargingStation.getCity(), stationDTO.getCity());
                    assertEquals(chargingStation.getFreeSlots(), stationDTO.getFreeSlots());
                });
    }

    @Test
    public void findAll_ShouldReturnOneObjectCalledListStationsForAdminResponseDTO_WhenStationsExistInDatabase() {
        List<StationForAdminResponseDTO> listOfStationsDTO = new ArrayList<>();
        chargingStationRepository.deleteAll();

        ChargingStation chargingStation2 = new ChargingStation(null, "station name2", "station address2",
                "station city2", 1000, new ArrayList<>());
        chargingStationRepository.save(chargingStation2);

        StationForAdminResponseDTO station = modelMapper.map(chargingStation2, StationForAdminResponseDTO.class);
        listOfStationsDTO.add(station);

        ListStationsForAdminResponseDTO expected = new ListStationsForAdminResponseDTO(listOfStationsDTO);

        webTestClient.get()
                .uri("/api/admin/stations")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ListStationsForAdminResponseDTO.class)
                .consumeWith(response -> {
                    ListStationsForAdminResponseDTO responseList = response.getResponseBody();
                    assertNotNull(responseList);
                    assertIterableEquals(expected.getListOfStationsDTOs(), responseList.getListOfStationsDTOs());
                });
    }
}