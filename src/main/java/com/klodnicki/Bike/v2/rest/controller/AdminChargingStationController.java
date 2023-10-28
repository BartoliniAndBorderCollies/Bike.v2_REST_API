package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.station.ListStationsForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.interfacee.GenericChargingStationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stations")
@AllArgsConstructor
public class AdminChargingStationController {

    private final GenericChargingStationService chargingStationService;

    @PostMapping("/list/add/{id}")
    public ChargingStation addBikeToList(@PathVariable("id") Long chargingStationId, @RequestParam Long bikeId) {
        return chargingStationService.addBikeToList(chargingStationId, bikeId);
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
    public ListStationsForAdminResponseDTO findAll() {
        List<StationForAdminResponseDTO> stations = chargingStationService.findAll();

        return new ListStationsForAdminResponseDTO(stations);
    }
}
