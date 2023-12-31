package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.Authority;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.AuthorityRepository;
import com.klodnicki.Bike.v2.repository.UserRepository;
import configuration.IntegrationTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
@Import(IntegrationTestConfig.class)
class UserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private User user;
    @Autowired
    private AuthorityRepository authorityRepository;

    @BeforeEach
    public void setUp() {
        Authority authority = new Authority(null, "ROLE_ADMIN");
        Set<Authority> authoritySet = new HashSet<>();
        authoritySet.add(authority);

        String password = "123456";
        user = new User(null, "user name", "phone nr", "email@email.pl", password,
                authoritySet, 1234567, true, 100.00, null, null);
        userRepository.save(user);
    }

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
        authorityRepository.deleteAll();
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
                    assertEquals(1, userRepository.count());
                    assertEquals(expected, userDTO);
                    assertTrue(savedUser.isPresent());
                });
    }

    @Test
    public void findById_ShouldMapAndReturnUserForAdminResponseDTO_WhenUserIdIsGivenAndUserExistsInDatabase() {
        UserForAdminResponseDTO expected = modelMapper.map(user, UserForAdminResponseDTO.class);

        webTestClient.get()
                .uri("/user/" + user.getId())
                .exchange()
                .expectBody(UserForAdminResponseDTO.class)
                .consumeWith(response -> {
                    UserForAdminResponseDTO userDTO = response.getResponseBody();
                    assertNotNull(userDTO);
                    assertEquals(expected, userDTO);
                });
    }
}