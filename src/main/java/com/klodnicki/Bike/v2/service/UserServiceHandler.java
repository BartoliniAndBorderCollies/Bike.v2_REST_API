package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.repository.UserRepository;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles the services related to User.
 * It implements the UserServiceApi interface.
 * It provides methods to add a user, find all users, find a user by its ID, delete a user by its ID, ban a user, and save a user.
 */
@Service
@AllArgsConstructor
public class UserServiceHandler implements UserServiceApi {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * This method adds a new {@link User} object to the repository and returns a {@link UserForAdminResponseDTO} object.
     * It first saves the User object to the repository, then maps the saved User object to a UserForAdminResponseDTO object using a model mapper.
     *
     * @param user The User object to be added to the repository.
     * @return A UserForAdminResponseDTO object that represents the added User.
     */
    @Override
    public UserForAdminResponseDTO add(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User user1 = userRepository.save(user);

        return modelMapper.map(user1, UserForAdminResponseDTO.class);
    }

    /**
     * This method retrieves all {@link User} objects from the repository and returns a list of {@link UserForAdminResponseDTO} objects.
     * It first retrieves all User objects from the repository, then iterates over each User object, maps it to a UserForAdminResponseDTO object using a model mapper, and adds it to a list.
     *
     * @return A list of UserForAdminResponseDTO objects that represent all the User objects in the repository.
     */
    @Override
    public List<UserForAdminResponseDTO> findAll() {
        Iterable<User> users = userRepository.findAll();
        List<UserForAdminResponseDTO> listUsersDTO = new ArrayList<>();

        for (User user: users) {
            UserForAdminResponseDTO userDto = modelMapper.map(user, UserForAdminResponseDTO.class);
            listUsersDTO.add(userDto);
        }
        return listUsersDTO;
    }
    /**
     * This method retrieves a {@link User} object from the repository by its ID and returns a {@link UserForAdminResponseDTO} object.
     * It first retrieves the User object using its ID, then maps the User object to a UserForAdminResponseDTO object using a model mapper.
     *
     * @param id The ID of the User object to be retrieved from the repository.
     * @return A UserForAdminResponseDTO object that represents the retrieved User.
     * @throws NotFoundInDatabaseException If no User with the given ID is found.
     */
    @Override
    public UserForAdminResponseDTO findById(Long id) throws NotFoundInDatabaseException {
        User user = findUserById(id);

        return modelMapper.map(user, UserForAdminResponseDTO.class);
    }
    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    @Override
    public User findUserById(Long id) throws NotFoundInDatabaseException {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundInDatabaseException(User.class));
    }

    /**
     * This method bans a {@link User} object by setting its account validity to false.
     * It first retrieves the User object using its ID, then sets the account validity of the User object to false, and saves the changes to the repository.
     * It returns a ResponseEntity with a success message and HTTP status code.
     *
     * @param id The ID of the User object to be banned.
     * @return A ResponseEntity with a success message and HTTP status code.
     * @throws NotFoundInDatabaseException If no User with the given ID is found.
     */
    @Override
    public ResponseEntity<?> banUser(Long id) throws NotFoundInDatabaseException {
        User user = findUserById(id);

        user.setAccountValid(false);
        userRepository.save(user);

        return new ResponseEntity<>("User banned successfully", HttpStatus.OK);
    }
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}
