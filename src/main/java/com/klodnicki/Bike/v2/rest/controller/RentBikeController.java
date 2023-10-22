package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
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

    @GetMapping("/bikes/{id}")
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(@PathVariable Long id) {
        return rentBikeService.findBikeForNormalUserById(id);
    }

    @PostMapping("/rentals/add")
    public RentResponseDTO rentBike(@RequestBody RentRequest rentRequest) {
        return rentBikeService.rentBike(rentRequest);
    }

    @PutMapping("/bikes/list/add/{id}")
    public ChargingStation addBikeToList(@PathVariable("id") Long chargingStationId, @RequestBody Bike bike) {
        return rentBikeService.addBikeToList(chargingStationId, bike);
    }

    @PutMapping("/rentals/{id}")
    public RentResponseDTO updateRent(@PathVariable Long id, @RequestBody Rent rent) {
        return rentBikeService.updateRent(id, rent);
    }

    @PutMapping("/returns/{rentId}")
    public void returnBike(@PathVariable Long rentId, @RequestParam Long returnChargingStationId, @RequestBody Long bikeId) {
        rentBikeService.returnBike(rentId, returnChargingStationId, bikeId);
    }


}
