package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminChargingStationControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    private ChargingStation chargingStation;
    private Bike bike;

    @BeforeEach
    public void setUp() {
        chargingStation = new ChargingStation(1L, "station name", "station address", "station city",
                100, new ArrayList<>());
        chargingStationRepository.save(chargingStation);

        bike = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bikeRepository.save(bike);
    }

    @Test
    public void addBikeToList_ShouldAddBikeToStationListOfBikesAndReturnChargingStation_WhenStationIdAndBikeIdAreGiven() {
        List<Bike> bikeList = new ArrayList<>();
        bikeList.add(bike);
        ChargingStation chargingStation2 = new ChargingStation(2L, "station name2", "station address2",
                "station city2", 100,  bikeList);
        chargingStationRepository.save(chargingStation2);

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
                            assertFalse(bikeList.isEmpty());
                            assertIterableEquals(chargingStation2.getBikeList(), chargingStation.getBikeList());
                        }
                );
    }
}