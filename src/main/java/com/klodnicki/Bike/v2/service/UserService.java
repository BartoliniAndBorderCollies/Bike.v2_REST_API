package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    ModelMapper modelMapper = new ModelMapper();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User add(User user) {
        return userRepository.save(user);
    }

    public List<UserForAdminResponseDTO> findAll() {
        Iterable<User> users = userRepository.findAll();
        List<UserForAdminResponseDTO> listUsersDTO = new ArrayList<>();

        for (User user: users) {
            UserForAdminResponseDTO userDto = modelMapper.map(user, UserForAdminResponseDTO.class);
            listUsersDTO.add(userDto);
        }
        return listUsersDTO;
    }

    public User findById(Long id) {
        return getUser(id);
    }

    private User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }

    public ResponseEntity<?> banUser(Long id) {
        User user = getUser(id);

        user.setAccountValid(false);
        userRepository.save(user);

        if(user.isAccountValid()) {
            return new ResponseEntity<>("Failed to ban user", HttpStatus.I_AM_A_TEAPOT);
        }
        return new ResponseEntity<>("User banned successfully", HttpStatus.OK);
    }
}
