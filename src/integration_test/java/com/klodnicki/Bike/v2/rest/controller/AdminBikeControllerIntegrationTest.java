package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.Authority;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.AuthorityRepository;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import com.klodnicki.Bike.v2.service.UserServiceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Base64;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
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
    @Autowired
    private UserServiceHandler userServiceHandler;
    private ChargingStation chargingStation;
    private Bike bike;
    private String basicAuthHeader;
    @Autowired
    private AuthorityRepository authorityRepository;
    private Authority authority;

    @BeforeEach
    void setUp() {
        chargingStation = new ChargingStation(null, "station name", "station address", "station city", 100, new ArrayList<>());
        chargingStationRepository.save(chargingStation);
        bike = new Bike(null, BikeType.ELECTRIC, null, null, chargingStation);
        bikeRepository.save(bike);

        authority = new Authority(null, "ROLE_ADMIN");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);

        User user = new User(null, "test name1", "phone number", "email", "password",
                authoritySet,11223344, true, "user", 100.00, null, null);

        userServiceHandler.add(user);

        basicAuthHeader = "Basic " + Base64.getEncoder()
                .encodeToString((user.getEmailAddress() + ":" + "password").getBytes());// I need to provide a raw password,
        // using getPassword() would take hash coded
    }

    @AfterEach
    void clearDatabase() {
        bikeRepository.deleteAll();
        chargingStationRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addBike_ShouldAddBikeToDatabaseAndReturnBikeForAdminResponseDTO_WhenBikeRequestDTOIsProvided() {
        StationForAdminResponseDTO stationDTO = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        BikeRequestDTO bikeRequestDTO = new BikeRequestDTO(1L, "test serialNumber", false,
                BikeType.ELECTRIC, 0, new GpsCoordinates("10N", "5E"),
                null, stationDTO);

        webTestClient.post()
                .uri("/api/admin/bikes/add")
                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
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
        StationForAdminResponseDTO stationDTO = modelMapper.map(chargingStation, StationForAdminResponseDTO.class);

        webTestClient.get()

                .uri("/api/admin/bikes/" + bike.getId())
                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BikeForAdminResponseDTO.class)
                .consumeWith(response -> {
                    BikeForAdminResponseDTO bikeDTO = response.getResponseBody();
                    assertNotNull(bikeDTO);
                    assertEquals(bike.getId(), bikeDTO.getId());
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
                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
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
        // I have to set charging station to null, otherwise I'm not able to delete bike because it holds foreign key
        // of charging station
        bike.setChargingStation(null);
        bikeRepository.save(bike);

        //Act
        webTestClient.delete()
                .uri("/api/admin/bikes/" + bike.getId())
                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    //after deleting the bike, I try to retrieve it from the database again using
                    // bikeRepository.findById(id). This returns an Optional<Bike>
                    Optional<Bike> deletedBike = bikeRepository.findById(bike.getId());
                    // If the bike was successfully deleted, this Optional should be empty, so I assert that
                    //deletedBike.isEmpty() is true
                    assertTrue(deletedBike.isEmpty());
                });
    }

    @Test
    void updateBikeById_ShouldUpdateBike_WhenBikeExistInDatabaseAndBikeIdAndBikeRequestDTOIsGiven() {
        //Arrange
        User user = new User(null, "user name", "123-456-789", "email@email.pl",
                123456678, true, "user", 100.00, null, null);
        userRepository.save(user);
        UserForAdminResponseDTO userDTO = modelMapper.map(user, UserForAdminResponseDTO.class);

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
                .header(HttpHeaders.AUTHORIZATION, basicAuthHeader)
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