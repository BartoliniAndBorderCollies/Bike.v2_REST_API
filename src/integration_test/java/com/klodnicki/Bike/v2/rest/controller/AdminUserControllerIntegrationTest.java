package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.ListUsersForAdminResponseDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "/application-test.properties")
class AdminUserControllerIntegrationTest {

    @Autowired
    private WebTestClient webTestClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;
    private User user1;
    private User user2;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        user1 = new User(null, "test name1", "phone number", "email@email.pl",
                11223344, true, "user", 100.00, null, null);
        user2 = new User(null, "test name2", "phone number2", "email2@email.pl",
                11223344, true, "user2", 0.00, null, null);

        userRepository.save(user1);
        userRepository.save(user2);
    }

    @AfterEach
    public void clearDatabase() {
        userRepository.deleteAll();
    }

    @Test
    public void findAllUsers_ShouldMapAndReturnListUsersForAdminResponseDTO_WhenUsersExistInDatabase() {
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userList.add(user2);

        List<UserForAdminResponseDTO> listUsersDTO = new ArrayList<>();
        for (User user : userList) {
            UserForAdminResponseDTO userDTO = modelMapper.map(user, UserForAdminResponseDTO.class);
            listUsersDTO.add(userDTO);
        }

        ListUsersForAdminResponseDTO expected = new ListUsersForAdminResponseDTO(listUsersDTO);

        webTestClient.get()
                .uri("/api/admin/users")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ListUsersForAdminResponseDTO.class)
                .consumeWith(response -> {
                    ListUsersForAdminResponseDTO responseDTO = response.getResponseBody();
                    assertNotNull(responseDTO);
                    assertIterableEquals(expected.getListOfUsersDTOs(), responseDTO.getListOfUsersDTOs());
                });
    }

    @Test
    public void banUser_ShouldReturnResponseEntityAsStringAndSetAccountValidToFalse_WhenUserIdIsGiven() {
        webTestClient.put()
                .uri("/api/admin/users/" + user1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
                    String responseMessage = response.getResponseBody();
                    User updatedUser = userRepository.findById(user1.getId()).orElseThrow(IllegalArgumentException::new);
                    assertNotNull(responseMessage);
                    assertFalse(updatedUser.isAccountValid());
                    assertEquals("User banned successfully", responseMessage);
                });
    }

    @Test
    public void deleteUser_ShouldDeleteUserFromDatabase_WhenUserIdIsGiven() {
        webTestClient.delete()
                .uri("/api/admin/users/" + user1.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    Optional<User> deletedUser = userRepository.findById(user1.getId());
                    assertTrue(deletedUser.isEmpty());
                });
    }
}