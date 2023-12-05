package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.*;
import com.klodnicki.Bike.v2.repository.BikeRepository;
import com.klodnicki.Bike.v2.repository.ChargingStationRepository;
import com.klodnicki.Bike.v2.repository.RentRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private UserRepository userRepository;
    @Autowired
    private RentRepository rentRepository;
    @Autowired
    private ModelMapper modelMapper;
    private Bike bike;
    private ChargingStation chargingStation;
    private User user;

    @BeforeEach
    public void setUp() {
        String password = "password";
        chargingStation = new ChargingStation(null, "station name", "station address", "station city",
                100, new ArrayList<>());
        chargingStationRepository.save(chargingStation);

        bike = new Bike(null, BikeType.ELECTRIC, null, null, null);
        bike.setRented(true);
        bikeRepository.save(bike);

        Authority authority = new Authority(null, "ROLE_ADMIN");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);

        user = new User(null, "user name", "user phone nr", "email@email.pl",
                password, authoritySet, 12345, true, 100.00,null, null);
        userRepository.save(user);
    }

    @AfterEach
    public void clearTestDatabase() {
        userRepository.deleteAll();
        chargingStationRepository.deleteAll();
        rentRepository.deleteAll();
        bikeRepository.deleteAll();
    }

    @Test
    public void findAvailableBikes_ShouldBuildAndReturnNotRentedBikesInListBikesForNormalUserResponseDTO_WhenBikesExistInDatabase() {

        Bike bike1NotRented = new Bike(1L, BikeType.ELECTRIC, null, null, null);
        Bike bike2NotRented = new Bike(2L, BikeType.TRADITIONAL, null, null, null);
        Bike bike3Rented = new Bike(3L, BikeType.ELECTRIC, null, null, null);
        bike1NotRented.setRented(false);
        bike2NotRented.setRented(false);
        bike3Rented.setRented(true);

        bikeRepository.save(bike1NotRented);
        bikeRepository.save(bike2NotRented);
        bikeRepository.save(bike3Rented);

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

    @Test
    public void findBikeForNormalUserById_ShouldMapAndReturnBikeForNormalUserResponseDTO_WhenBikeExistsInDatabaseAndBikeIdIsGiven() {
        BikeForNormalUserResponseDTO expected = modelMapper.map(bike, BikeForNormalUserResponseDTO.class);

        webTestClient.get()
                .uri("/api/bikes/" + bike.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BikeForNormalUserResponseDTO.class)
                .consumeWith(response -> {
                    BikeForNormalUserResponseDTO bikeResponseDTO = response.getResponseBody();
                    assertNotNull(bikeResponseDTO);
                    assertEquals(expected, bikeResponseDTO);
                });
    }

    @Test
    public void rentBike_ShouldInstantiateAndReturnRentResponseDTOAndSaveRentInDatabase_WhenRentRequestIsGiven() {

        bike.setChargingStation(chargingStation);
        bikeRepository.save(bike);
        RentRequest rentRequest = new RentRequest(1L, user.getId(), bike.getId(), 10);

        webTestClient.post()
                .uri("/api/rentals/add")
                .bodyValue(rentRequest)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RentResponseDTO.class)
                .consumeWith(response -> {
                    RentResponseDTO responseDTO = response.getResponseBody();
                    assertNotNull(responseDTO);
                    assertEquals(1, rentRepository.count());
                    assertEquals(rentRequest.getDaysOfRent(), responseDTO.getDaysOfRent());
                    assertEquals(user.getName(), responseDTO.getUserForNormalUserResponseDTO().getName());
                    assertIterableEquals(user.getAuthorities(), responseDTO.getUserForNormalUserResponseDTO().getAuthorities());
                    assertEquals(bike.getSerialNumber(), responseDTO.getBikeForNormalUserResponseDTO().getSerialNumber());
                    assertEquals(bike.getBikeType(), responseDTO.getBikeForNormalUserResponseDTO().getBikeType());
                });
    }

    @Test
    public void updateRent_ShouldUpdateDaysOfRentAndMapAndReturnRentResponseDTO_WhenRentIdAndRentRequestDTOIsGiven() {

        Rent rent = new Rent(null, LocalDateTime.of(2023, 11, 23, 10, 0, 0),
                null, 10, 100.00, bike, user, null);
        rentRepository.save(rent);

        RentRequestDTO rentRequestDTO = new RentRequestDTO(1L, LocalDateTime.of(2023, 11, 23,
                10, 0, 0), null, 5, null,
                null, null);

        RentResponseDTO expected = new RentResponseDTO(1L, rentRequestDTO.getRentalStartTime(),
                rentRequestDTO.getRentalEndTime(), rentRequestDTO.getDaysOfRent(), rentRequestDTO.getBikeForNormalUserResponseDTO(),
                rentRequestDTO.getUserForNormalUserResponseDTO(), rentRequestDTO.getStationForNormalUserResponseDTO());

        webTestClient.put()
                .uri("/api/rentals/" + rent.getId())
                .bodyValue(rentRequestDTO)
                .exchange()
                .expectStatus().isOk()
                .expectBody(RentResponseDTO.class)
                .consumeWith(response -> {
                    RentResponseDTO responseDTO = response.getResponseBody();
                    assertNotNull(responseDTO);
                    assertEquals(expected.getDaysOfRent(), responseDTO.getDaysOfRent());
                });

        rent.setBike(null);
        rent.setUser(null);
        rentRepository.save(rent);
    }

    @Test
    public void returnBike_ShouldReturnResponseEntityAsStringAndReturnBikeToStation_WhenRentAndStationIdsAreGiven() {

        Rent rent = new Rent(null, LocalDateTime.of(2023, 11, 23, 10, 0, 0),
                null, 10, 100.00, bike, user, null);
        rentRepository.save(rent);

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/returns/" + rent.getId())
                        .queryParam("returnChargingStationId", chargingStation.getId())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseMessage = response.getResponseBody();
                    ChargingStation returnChargingStation =
                            chargingStationRepository.findById(chargingStation.getId()).orElseThrow(IllegalArgumentException::new);
                    assertNotNull(responseMessage);
                    assertEquals("Bike successfully returned.", responseMessage);
                    assertEquals(chargingStation.getFreeSlots() - 1, returnChargingStation.getFreeSlots());
                    assertEquals(rent.getAmountToBePaid(), returnChargingStation.getRent().getAmountToBePaid());
                    assertEquals(rent.getUser().getBalance() - rent.getAmountToBePaid(), returnChargingStation.getRent().getUser().getBalance());
                    assertFalse(returnChargingStation.getBikeList().isEmpty());
                    //I use stream in assertion to check if on the bikeList is the bike with the specific id
                    assertTrue(returnChargingStation.getBikeList().stream().anyMatch(b -> b.getId().equals(bike.getId())));
                });
        rent.setBike(null);
        rent.setUser(null);
        bike.setChargingStation(null);
        rentRepository.save(rent);
        bikeRepository.save(bike);
    }
}