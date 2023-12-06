package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
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

    private UserRepository userRepository;
    private UserServiceHandler userServiceHandler;
    private ModelMapper modelMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        userServiceHandler = new UserServiceHandler(userRepository, modelMapper);

        user = mock(User.class);
    }

    @Test
    public void add_ShouldReturnUserForAdminResponseDTO_WhenUserProvided() {
        //Arrange
        UserForAdminResponseDTO userDTO = new UserForAdminResponseDTO();
        when(userRepository.save(user)).thenReturn(user);
        when(modelMapper.map(user, UserForAdminResponseDTO.class)).thenReturn(userDTO);

        //Act
        UserForAdminResponseDTO actual = userServiceHandler.add(user);

        //Assert
        assertEquals(userDTO, actual);
    }

    @Test
    public void findAll_ShouldReturnListOfUserForAdminResponseDTO_WhenProvidedIterableUsers() {
        // Arrange
        Iterable<User> iterableOfUsers = createUsers();
        List<UserForAdminResponseDTO> expected = mapUsersToDTO(iterableOfUsers);

        when(userRepository.findAll()).thenReturn(iterableOfUsers);

        // Act
        List<UserForAdminResponseDTO> actual = userServiceHandler.findAll();

        // Assert
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
        UserForAdminResponseDTO userDTO = new UserForAdminResponseDTO();

        for (User user : users) {
            when(modelMapper.map(user, UserForAdminResponseDTO.class)).thenReturn(userDTO);
            userDTOs.add(userDTO);
        }
        return userDTOs;
    }

    @Test
    public void findById_ShouldReturnUserForAdminResponseDTO_WhenProvidedId() throws NotFoundInDatabaseException {
        //Arrange
        UserForAdminResponseDTO userDTO = new UserForAdminResponseDTO();

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserForAdminResponseDTO.class)).thenReturn(userDTO);

        //Act
        UserForAdminResponseDTO actual = userServiceHandler.findById(user.getId());

        //Assert
        assertEquals(userDTO, actual);
    }

    @Test
    public void deleteById_ShouldCallOnUserRepositoryExactlyOnce_WhenProvidedId() {
        // Arrange
        // Act
        userServiceHandler.deleteById(user.getId());

        // Assert
        verify(userRepository, times(1)).deleteById(user.getId());
    }

    @Test
    public void findUserById_ShouldThrowNotFoundInDbException_WhenUserIsNotFound() {
        assertThrows(NotFoundInDatabaseException.class, () -> userServiceHandler.findUserById(1L));
    }

    @Test
    public void banUser_ShouldReturnResponseEntityWithCodeOK_WhenProvidedId() throws NotFoundInDatabaseException {
        //Arrange
        User user = mock(User.class);
        user.setId(1L);
        user.setAccountValid(false);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        //Act
        ResponseEntity<?> response = userServiceHandler.banUser(user.getId());

        //Assert
        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
    }

    @Test
    public void save_ShouldCallOnUserRepositoryExactlyOnce_WhenProvidedUser() {
        //Arrange
        when(userRepository.save(user)).thenReturn(user);

        //Act
        userServiceHandler.save(user);

        //Assert
        verify(userRepository, times(1)).save(user);
    }
}