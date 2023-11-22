package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class RentBikeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private ModelMapper modelMapper;
    private Bike bike1NotRented;
    private Bike bike2NotRented;
    private Bike bike3Rented;

    @BeforeEach
    public void setUp() {
        ChargingStation chargingStation = new ChargingStation(null, "station name", "station address", "station city",
                100, new ArrayList<>());
        chargingStationRepository.save(chargingStation);

        bike1NotRented = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bike2NotRented = new Bike(null, BikeType.TRADITIONAL, null, null, chargingStation);
        bike3Rented = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bike1NotRented.setRented(false);
        bike2NotRented.setRented(false);
        bike3Rented.setRented(true);

        bikeRepository.save(bike1NotRented);
        bikeRepository.save(bike2NotRented);
        bikeRepository.save(bike3Rented);
    }

    @Test
    public void findAvailableBikes_ShouldReturnOneObjectCalledListBikesForNormalUserResponseDTOAndMustBeNotRented_WhenBikesExistInDatabase() {
        List<BikeForNormalUserResponseDTO> dtoList = new ArrayList<>();
        List<Bike> bikeList = new ArrayList<>();
        bikeList.add(bike1NotRented);
        bikeList.add(bike2NotRented);

        for (Bike bike : bikeList) {
            BikeForNormalUserResponseDTO bikeDTO = BikeForNormalUserResponseDTO.builder()
                    .id(bike.getId())
                    .serialNumber(bike.getSerialNumber())
                    .isRented(bike.isRented())
                    .bikeType(bike.getBikeType())
                    .build();
            dtoList.add(bikeDTO);
        }

        ListBikesForNormalUserResponseDTO expected = new ListBikesForNormalUserResponseDTO(dtoList);

        webTestClient.get()
                .uri("/api/bikes")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ListBikesForNormalUserResponseDTO.class)
                .consumeWith(response -> {
                    ListBikesForNormalUserResponseDTO responseListDTO = response.getResponseBody();
                    assertNotNull(responseListDTO);
                    assertFalse(responseListDTO.getListOfBikesDTOs().isEmpty());
                    assertIterableEquals(expected.getListOfBikesDTOs(), responseListDTO.getListOfBikesDTOs());
                });
    }

}