package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.service.BikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bike")
public class BikeController {

    private final BikeService bikeService;

    public BikeController(BikeService bikeService) {
        this.bikeService = bikeService;
    }

    @PostMapping("/admin/add")
    public BikeForAdminResponseDTO addBike(@RequestBody Bike bike) {
        return (BikeForAdminResponseDTO) bikeService.add(bike);
    }

    @GetMapping("/admin/{id}")
    public BikeForAdminResponseDTO findBikeById (@PathVariable Long id) {
        return bikeService.findById(id);
    }

    @GetMapping("/admin")
    public List<BikeForAdminResponseDTO> findAllBikes() {
        return bikeService.findAll();
    }

    @DeleteMapping("/admin/{id}")
    public void deleteBikeById(@PathVariable Long id) {
        bikeService.deleteById(id);
    }

    @PutMapping("/admin/{id}")
    public BikeForAdminResponseDTO updateBikeById(@PathVariable Long id, @RequestBody Bike bike) {
        return bikeService.update(id, bike);
    }



}
