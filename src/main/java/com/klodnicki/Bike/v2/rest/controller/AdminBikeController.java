package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikesForAdminResponseDTO;
import com.klodnicki.Bike.v2.service.GenericBikeService;
import com.klodnicki.Bike.v2.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/bikes")
public class AdminBikeController {

    private final GenericBikeService bikeService;

    private final UserService userService;

    public AdminBikeController(GenericBikeService bikeService, UserService userService) {
        this.bikeService = bikeService;
        this.userService = userService;
    }

    @PostMapping("/add")
    public BikeForAdminResponseDTO addBike(@RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.add(bikeDTO);
    }

    @GetMapping("/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @GetMapping
    public BikesForAdminResponseDTO findAllBikes() {
        List<BikeForAdminResponseDTO> listBikesDTO = bikeService.findAll();

        return new BikesForAdminResponseDTO(listBikesDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @RequestBody BikeRequestDTO bikeDTO) {
        return bikeService.update(id, bikeDTO);
    }
}
