package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceHandlerTest {

    private UserRepository userRepository; // to jest mock
    private UserServiceHandler userServiceHandler;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        modelMapper = new ModelMapper();
        userServiceHandler = new UserServiceHandler(userRepository, modelMapper);
    }

    @Test
    public void add_ShouldReturnUserForAdminResponseDTO_WhenUserProvided() {
        //Arrange
        User user = new User();
        when(userRepository.save(user)).thenReturn(new User());

        UserForAdminResponseDTO expected = modelMapper.map(user, UserForAdminResponseDTO.class);

        //Act
        UserForAdminResponseDTO actual = userServiceHandler.add(user);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void findAll_ShouldReturnListOfUserForAdminResponseDTO_WhenProvidedIterableUsers() {
        //Arrange
        Iterable<User> iterableOfUsers = new ArrayList<>();
        List<UserForAdminResponseDTO> expected = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            User user = new User();
            ((ArrayList<User>) iterableOfUsers).add(user);
        }

        when(userRepository.findAll()).thenReturn(iterableOfUsers);

        for (User user : iterableOfUsers) {
            UserForAdminResponseDTO userDTO = modelMapper.map(user, UserForAdminResponseDTO.class);
            expected.add(userDTO);
        }

        //Act
        List<UserForAdminResponseDTO> actual = userServiceHandler.findAll();

        //Assert
        assertIterableEquals(expected, actual);
    }

    private Iterable<User> createUsers() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            users.add(new User());
        }
        return users;
    }

    private List<UserForAdminResponseDTO> mapUsersToDTO(Iterable<User> users) {
        List<UserForAdminResponseDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            UserForAdminResponseDTO userDTO = modelMapper.map(user, UserForAdminResponseDTO.class);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Test
    public void findById_ShouldReturnUserForAdminResponseDTO_WhenProvidedId() {
        //Arrange
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserForAdminResponseDTO expected = modelMapper.map(user, UserForAdminResponseDTO.class);

        //Act
        UserForAdminResponseDTO actual = userServiceHandler.findById(1L);

        //Assert
        assertEquals(expected, actual);
    }

    @Test
    public void deleteById_ShouldCallOnUserRepositoryExactlyOnce_WhenProvidedId() {
        //Arrange
        User user = new User();

        //Act
        userServiceHandler.deleteById(user.getId());

        //Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void findUserById_ShouldThrowException_WhenUserIsNotFound() {
        assertThrows(IllegalArgumentException.class, () -> userServiceHandler.findUserById(1L));
    }

    @Test
    public void banUser_ShouldReturnResponseEntityWithCodeOK_WhenProvidedId() {
        //Arrange
        User user = new User();
        user.setId(1L);
        user.setAccountValid(false);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        //Act
        ResponseEntity<?> response = userServiceHandler.banUser(user.getId());

        //Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    public void save_ShouldCallOnUserRepositoryExactlyOnce_WhenProvidedUser() {
        //Arrange
        User user = new User();

        //Act
        userServiceHandler.save(user);

        //Assert
        verify(userRepository, times(1)).save(user);
    }


}