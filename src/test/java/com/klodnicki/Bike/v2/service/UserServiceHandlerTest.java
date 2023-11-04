package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class UserServiceHandlerTest {

    private UserRepository userRepository; // to jest mock
    private UserServiceHandler userServiceHandler;
    private ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        userRepository = mock(UserRepository.class);
        userServiceHandler = new UserServiceHandler(userRepository, new ModelMapper());
        modelMapper = new ModelMapper();
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
}