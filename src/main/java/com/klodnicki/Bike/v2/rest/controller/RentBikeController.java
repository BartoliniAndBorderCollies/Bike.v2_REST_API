package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.service.GenericBikeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentBikeController {

    private final GenericBikeService bikeService;

    public RentBikeController(GenericBikeService bikeService) {
        this.bikeService = bikeService;
    }


    @GetMapping("/bicycles")
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        return bikeService.findAvailableBikes();
    }


}
