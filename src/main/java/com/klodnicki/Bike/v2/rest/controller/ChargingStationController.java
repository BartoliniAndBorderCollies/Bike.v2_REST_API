package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.ChargingStationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/station")
public class ChargingStationController {

    private final ChargingStationService chargingStationService;

    public ChargingStationController(ChargingStationService chargingStationService) {
        this.chargingStationService = chargingStationService;
    }

    @PostMapping("/add")
    public ChargingStation add(@RequestBody ChargingStation chargingStation) {
        return chargingStationService.add(chargingStation);
    }

    @GetMapping
    public Iterable<ChargingStation> findAll() {
        return chargingStationService.findAll();
    }



}
