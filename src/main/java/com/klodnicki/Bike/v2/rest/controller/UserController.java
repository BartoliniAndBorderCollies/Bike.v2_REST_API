package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserServiceApi userService;

    /**
     * This method is used to add a new user.
     * It takes a User object as input and returns a UserForAdminResponseDTO object.
     * User must fulfil some requirements since it is preceded by @Valid.
     *
     * @param user This is a request object which contains the details of the user to be added.
     * @return UserForAdminResponseDTO This returns the response object with details of the added user.
     *
     * @PostMapping("/add")
     */
    @PostMapping("/add")
    public UserForAdminResponseDTO add(@Valid @RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/{id}")
    public UserForAdminResponseDTO findById(@PathVariable("id") Long id) throws NotFoundInDatabaseException {
        return userService.findById(id);
    }
}
