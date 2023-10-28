package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserServiceApi userService;

    @PostMapping("/add")
    public UserForAdminResponseDTO add(@RequestBody User user) {
        return userService.add(user);
    }

    @GetMapping("/{id}")
    public UserForAdminResponseDTO findById(@PathVariable("id") Long id) {
        return userService.findById(id);
    }
}
