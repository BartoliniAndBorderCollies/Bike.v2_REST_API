package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.RentBikeGenericService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RentBikeController {

    private final RentBikeGenericService rentBikeService;

    public RentBikeController(RentBikeGenericService rentBikeService) {
        this.rentBikeService = rentBikeService;
    }


    @GetMapping("/bikes")
    public ListBikesForNormalUserResponseDTO findAvailableBikes() {
        List<BikeForNormalUserResponseDTO> bikeDTOs = rentBikeService.findAvailableBikes();

        return new ListBikesForNormalUserResponseDTO(bikeDTOs);
    }

    @GetMapping("/bikes/{id}")
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(@PathVariable Long id) {
        return rentBikeService.findBikeForNormalUserById(id);
    }

    @PostMapping("/rentals/add")
    public RentResponseDTO rentBike(@RequestBody RentRequest rentRequest) {
        return rentBikeService.add(rentRequest);
    }

    @PutMapping("/bikes/list/add/{id}")
    public ChargingStation addBikeToList(@PathVariable("id") Long chargingStationId, @RequestBody Bike bike) {
        return rentBikeService.addBikeToList(chargingStationId, bike);
    }

    @PutMapping("/rentals/{id}")
    public RentResponseDTO updateRent(@PathVariable Long id, @RequestBody RentRequestDTO rentRequestDTO) {
        return rentBikeService.updateRent(id, rentRequestDTO);
    }

    @PutMapping("/returns/{rentId}")
    public ResponseEntity<?> returnBike(@PathVariable Long rentId, @RequestParam Long returnChargingStationId, @RequestBody Long bikeId) {
        return rentBikeService.returnBike(rentId, returnChargingStationId, bikeId);
    }
}
