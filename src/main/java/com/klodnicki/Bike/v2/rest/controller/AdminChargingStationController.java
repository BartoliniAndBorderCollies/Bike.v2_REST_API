package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.station.ListStationsForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.api.ChargingStationServiceApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/stations")
@AllArgsConstructor
public class AdminChargingStationController {

    private final ChargingStationServiceApi chargingStationService;

    @PostMapping("/list/add/{id}")
    public ChargingStation addBikeToList(@PathVariable("id") Long chargingStationId, @RequestParam Long bikeId)
            throws NotFoundInDatabaseException {
        return chargingStationService.addBikeToList(chargingStationId, bikeId);
    }

    @PostMapping("/add")
    public StationForAdminResponseDTO add(@Valid @RequestBody ChargingStation chargingStation) {
        return chargingStationService.add(chargingStation);
    }

    @GetMapping("/{id}")
    public StationForAdminResponseDTO findById(@PathVariable("id") Long id) throws NotFoundInDatabaseException {
        return chargingStationService.findById(id);
    }

    @GetMapping
    public ListStationsForAdminResponseDTO findAll() {
        List<StationForAdminResponseDTO> stations = chargingStationService.findAll();

        return new ListStationsForAdminResponseDTO(stations);
    }
}
