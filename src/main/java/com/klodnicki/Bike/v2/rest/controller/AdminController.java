package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.service.GenericBikeService;
import com.klodnicki.Bike.v2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final GenericBikeService bikeService;

    private final UserService userService;

    public AdminController(GenericBikeService bikeService, UserService userService) {
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @PostMapping("/bicycles/add")
    public BikeForAdminResponseDTO addBike(@RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.add(bikeDTO);
    }

    @GetMapping("/bicycles/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @GetMapping("/bicycles")
    public List<BikeForAdminResponseDTO> findAllBikes() {
        return bikeService.findAll();
    }

    @DeleteMapping("/bicycles/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    @PutMapping("/bicycles/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.update(id, bikeDTO);
    }

    @GetMapping("/users")
    public List<UserForAdminResponseDTO> findAllUsers() {
        return userService.findAll();
    }
}
