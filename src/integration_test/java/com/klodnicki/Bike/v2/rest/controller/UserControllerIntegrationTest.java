package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User(null, "user name", "phone nr", "email", 123456,
                true, "user", 100.00, null, null);
        userRepository.save(user);
    }

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void add_ShouldAddUserToDatabaseAndMapAndReturnUserForAdminResponseDTO_WhenUserIsGiven() {
        UserForAdminResponseDTO expected = modelMapper.map(user, UserForAdminResponseDTO.class);

        webTestClient.post()
                .uri("/user/add")
                .bodyValue(user)
                .exchange()
                .expectBody(UserForAdminResponseDTO.class)
                .consumeWith(response -> {
                    UserForAdminResponseDTO userDTO = response.getResponseBody();
                    Optional<User> savedUser = userRepository.findById(user.getId());
                    assertNotNull(userDTO);
                    assertTrue(userRepository.count() > 0);
                    assertEquals(expected, userDTO);
                    assertTrue(savedUser.isPresent());
                });
    }
}