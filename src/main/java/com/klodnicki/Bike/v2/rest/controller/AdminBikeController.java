package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.service.GenericBikeService;
import com.klodnicki.Bike.v2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminBikeController {

    private final GenericBikeService bikeService;

    private final UserService userService;

    public AdminBikeController(GenericBikeService bikeService, UserService userService) {
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @PostMapping("/bikes/add")
    public BikeForAdminResponseDTO addBike(@RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.add(bikeDTO);
    }

    @GetMapping("/bikes/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @GetMapping("/bikes")
    public List<BikeForAdminResponseDTO> findAllBikes() {
        return bikeService.findAll();
    }

    @DeleteMapping("/bikes/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    @PutMapping("/bikes/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.update(id, bikeDTO);
    }

    @GetMapping("/users")
    public List<UserForAdminResponseDTO> findAllUsers() {
        return userService.findAll();
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<?> banUser(@PathVariable Long id) {
        return userService.banUser(id);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
