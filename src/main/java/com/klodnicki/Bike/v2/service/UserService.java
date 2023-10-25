package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import com.klodnicki.Bike.v2.service.serviceInterface.AddService;
import com.klodnicki.Bike.v2.service.serviceInterface.DeleteService;
import com.klodnicki.Bike.v2.service.serviceInterface.FindService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements AddService<UserForAdminResponseDTO, User>, FindService<UserForAdminResponseDTO>,
        DeleteService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public UserService(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public UserForAdminResponseDTO add(User user) {
        User user1 = userRepository.save(user);

        return modelMapper.map(user1, UserForAdminResponseDTO.class);
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

    public UserForAdminResponseDTO findById(Long id) {
        User user = getUser(id);

        return modelMapper.map(user, UserForAdminResponseDTO.class);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
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

    public User save(User user) {
        return userRepository.save(user);
    }
}
