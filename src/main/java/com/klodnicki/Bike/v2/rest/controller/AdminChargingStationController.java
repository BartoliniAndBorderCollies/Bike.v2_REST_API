package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.ChargingStationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/stations")
public class AdminChargingStationController {

    private final ChargingStationService chargingStationService;

    public AdminChargingStationController(ChargingStationService chargingStationService) {
        this.chargingStationService = chargingStationService;
    }

    @PostMapping("/add")
    public StationForAdminResponseDTO add(@RequestBody ChargingStation chargingStation) {
        return chargingStationService.add(chargingStation);
    }

    @GetMapping("/{id}")
    public StationForAdminResponseDTO findById(@PathVariable("id") Long id) {
        return chargingStationService.findById(id);
    }

    @GetMapping
    public Iterable<ChargingStation> findAll() {
        return chargingStationService.findAll();
    }
}
