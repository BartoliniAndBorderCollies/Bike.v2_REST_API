package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminBikeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ChargingStationRepository chargingStationRepository;

    @Test
    void addBike_ShouldAddBikeToDatabaseAndReturnBikeForAdminResponseDTO_WhenBikeRequestDTOIsProvided() {
        //I must create chargingStation and save it in repo, otherwise I get InvalidDataAccessApiUsageException:
        // org.hibernate.TransientPropertyValueException: object references an unsaved transient instance -
        // save the transient instance before flushing
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);
        StationForAdminResponseDTO stationDTO = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(1L, "test serialNumber", false,
                BikeType.ELECTRIC, 0, new GpsCoordinates("10N", "5E"),
                null, stationDTO);

        webTestClient.post()
                .uri("/api/admin/bikes/add")
                .bodyValue(bikeRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BikeForAdminResponseDTO.class)
                .consumeWith(response -> {
                    BikeForAdminResponseDTO bikeDTO = response.getResponseBody();
                    assertNotNull(bikeDTO);
                    assertEquals("test serialNumber", bikeDTO.getSerialNumber());
                    assertFalse(bikeDTO.isRented());
                    assertEquals(BikeType.ELECTRIC, bikeDTO.getBikeType());
                    assertEquals(0, bikeDTO.getAmountToBePaid());
                    assertEquals(new GpsCoordinates("10N", "5E"), bikeDTO.getGpsCoordinates());
                    assertNull(bikeDTO.getUserForAdminResponseDTO());
                    assertEquals(stationDTO, bikeDTO.getChargingStation());
                });
    }

    @Test
    void findAllBikes_ShouldReturnListOfBikesForAdminResponseDTO_WhenBikesExistInDatabase() {
        webTestClient.get()

                .uri("/api/admin/bikes")
                // tutaj możesz umieścić headery do tego URI powyżej
                // albo np. cookies
                .exchange()
                .expectStatus().isOk()
                .expectBody(ListBikesForAdminResponseDTO.class)
                .consumeWith(response -> {
                            ListBikesForAdminResponseDTO bikes = response.getResponseBody();
                            assertNotNull(bikes);
                            assertFalse(bikes.getBikesListDTOs().isEmpty());
                            // the rest of asserts
                        }
                );
    }
}