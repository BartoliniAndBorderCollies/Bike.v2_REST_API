package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;
import com.klodnicki.Bike.v2.model.entity.User;
import com.klodnicki.Bike.v2.service.RentBikeGenericService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentBikeController {

    private final RentBikeGenericService rentBikeService;

    public RentBikeController(RentBikeGenericService rentBikeService) {
        this.rentBikeService = rentBikeService;
    }


    @GetMapping("/bicycles")
    public List<BikeForNormalUserResponseDTO> findAvailableBikes() {
        return rentBikeService.findAvailableBikes();
    }

    @GetMapping("/bicycles/{id}")
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(@PathVariable Long id) {
        return rentBikeService.findBikeForNormalUserById(id);
    }

    @PostMapping("/rentals")
    public Rent rentBike(User user, Bike bike, ChargingStation chargingStation) {
        return null; //TODO: to be continued
    }


}
