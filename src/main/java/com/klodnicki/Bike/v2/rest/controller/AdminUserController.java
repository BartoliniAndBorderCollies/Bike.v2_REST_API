package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.ListUsersForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing users in the admin context.
 * It provides endpoints for retrieving all users, banning a user by their ID, and deleting a user by their ID.
 */
@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class AdminUserController {

    /**
     * Service for handling user-related operations.
     */
    private final UserServiceApi userService;

    /**
     * This method is used to retrieve all users.
     * It returns a ListUsersForAdminResponseDTO object which contains a list of UserForAdminResponseDTO objects.
     *
     * @return ListUsersForAdminResponseDTO This returns the response object with a list of all users.
     *
     * @GetMapping
     */
    @GetMapping
    public ListUsersForAdminResponseDTO findAllUsers() {
        List<UserForAdminResponseDTO> listOfUsers =  userService.findAll();

        return new ListUsersForAdminResponseDTO(listOfUsers);
    }

    /**
     * This method is used to ban a user by their ID.
     * It takes a user ID as input and returns a ResponseEntity object.
     *
     * @param id This is the ID of the user to be banned.
     * @return ResponseEntity This returns the response entity after the user has been banned.
     *
     * @PutMapping("/{id}")
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) throws NotFoundInDatabaseException {
        return userService.banUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }



}
