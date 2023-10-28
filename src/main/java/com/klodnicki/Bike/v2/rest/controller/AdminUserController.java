package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.user.ListUsersForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.service.api.UserServiceApi;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@AllArgsConstructor
public class AdminUserController {

    private final UserServiceApi userService;


    @GetMapping
    public ListUsersForAdminResponseDTO findAllUsers() {
        List<UserForAdminResponseDTO> listOfUsers =  userService.findAll();

        return new ListUsersForAdminResponseDTO(listOfUsers);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        return userService.banUser(id);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteById(id);
    }



}
