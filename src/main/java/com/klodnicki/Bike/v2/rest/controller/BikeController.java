package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.service.GenericBikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike/admin")
public class BikeController {

    private final GenericBikeService bikeService;

    public BikeController(GenericBikeService bikeService) {
        this.bikeService = bikeService;
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
    public List<BikeForAdminResponseDTO> findAllBikes() {
        return bikeService.findAll();
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
