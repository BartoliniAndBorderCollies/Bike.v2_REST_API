package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AdminBikeControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ChargingStationRepository chargingStationRepository;
    @Autowired
    private BikeRepository bikeRepository;
    @Autowired
    private UserRepository userRepository;

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
                    assertNull(bikeDTO.getUser());
                    assertEquals(stationDTO, bikeDTO.getChargingStation());
                });
    }

    @Test
    void findBikeById_ShouldReturnBikeForAdminResponseDTO_WhenBikeIdIsProvidedAndBikeExistInDatabase() {
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);
        StationForAdminResponseDTO stationDTO = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        Bike bike = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bikeRepository.save(bike);
        Long id = bike.getId();

        webTestClient.get()

                .uri("/api/admin/bikes/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BikeForAdminResponseDTO.class)
                .consumeWith(response -> {
                    BikeForAdminResponseDTO bikeDTO = response.getResponseBody();
                    assertNotNull(bikeDTO);
                    assertEquals(id, bikeDTO.getId());
                    assertEquals(BikeType.ELECTRIC, bikeDTO.getBikeType());
                    assertNull(bikeDTO.getRentalStartTime());
                    assertNull(bikeDTO.getUser());
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

    @Test
    void deleteBikeById_ShouldDeleteBikeInDatabase_WhenIdProvided() {
        //Arrange
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);

        // I create a bike which is at station
        Bike bike = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bikeRepository.save(bike);
        Long id = bike.getId();
        // I have to set charging station to null, otherwise I'm not able to delete bike because it holds foreign key
        // of charging station
        bike.setChargingStation(null);
        bikeRepository.save(bike);

        //Act
        webTestClient.delete()
                .uri("/api/admin/bikes/" + id)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    //after deleting the bike, I try to retrieve it from the database again using
                    // bikeRepository.findById(id). This returns an Optional<Bike>
                    Optional<Bike> deletedBike = bikeRepository.findById(id);
                    // If the bike was successfully deleted, this Optional should be empty, so I assert that
                    //deletedBike.isEmpty() is true
                    assertTrue(deletedBike.isEmpty());
                });
    }

    @Test
    void updateBikeById_ShouldUpdateBike_WhenBikeExistInDatabaseAndBikeIdAndBikeRequestDTOIsGiven() {
        //Arrange
        ChargingStation chargingStation = new ChargingStation();
        chargingStationRepository.save(chargingStation);

        User user = new User();
        userRepository.save(user);
        UserForAdminResponseDTO userDTO = modelMapper.map(user, UserForAdminResponseDTO.class);

        Bike bike = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bikeRepository.save(bike);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(bike.getId(), "updated serial number",
                true, BikeType.TRADITIONAL, 0, new GpsCoordinates("updated 100N", "updated 50W"),
                userDTO, null);

        //I must set station to null and bike user to user and save it. Otherwise, bike contains foreign key of station and user,
        // and I'm not able to update it.
        bike.setChargingStation(null);
        bike.setUser(user);
        bikeRepository.save(bike);

        //Act
        webTestClient.put()
                .uri("/api/admin/bikes/" + bike.getId())
                .bodyValue(bikeRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BikeForAdminResponseDTO.class)
                .consumeWith(response -> {
                    BikeForAdminResponseDTO actualUpdatedBike = response.getResponseBody();
                    assert actualUpdatedBike != null;
                    assertEquals(bikeRequestDTO.getId(), actualUpdatedBike.getId());
                    assertEquals(bikeRequestDTO.getSerialNumber(), actualUpdatedBike.getSerialNumber());
                    assertEquals(bikeRequestDTO.isRented(), actualUpdatedBike.isRented());
                    assertEquals(bikeRequestDTO.getBikeType(), actualUpdatedBike.getBikeType());
                    assertEquals(bikeRequestDTO.getAmountToBePaid(), actualUpdatedBike.getAmountToBePaid());
                    assertEquals(bikeRequestDTO.getGpsCoordinates(), actualUpdatedBike.getGpsCoordinates());
                    assertEquals(bikeRequestDTO.getUser(), actualUpdatedBike.getUser());
                    assertNull(actualUpdatedBike.getChargingStation());
                });
    }
}