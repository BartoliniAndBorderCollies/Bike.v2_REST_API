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

    /**
     * This method is used to add a bike to a charging station's list.
     * It takes a charging station ID and a bike ID as input and returns a ChargingStation object.
     *
     * @param chargingStationId This is the ID of the charging station where the bike will be added.
     * @param bikeId This is the ID of the bike to be added to the charging station's list.
     * @return ChargingStation This returns the updated charging station object after the bike has been added.
     *TODO: add throws
     * @PostMapping("/list/add/{id}")
     */
    @PostMapping("/list/add/{id}")
    public ChargingStation addBikeToList(@PathVariable("id") Long chargingStationId, @RequestParam Long bikeId)
            throws NotFoundInDatabaseException {
        return chargingStationService.addBikeToList(chargingStationId, bikeId);
    }

    /**
     * This method is used to add a new charging station.
     * It takes a ChargingStation object as input and returns a StationForAdminResponseDTO object.
     * ChargingStation needs to fulfil some requirements since it is preceded by @Valid.
     *
     * @param chargingStation This is a request object which contains the details of the charging station to be added.
     * @return StationForAdminResponseDTO This returns the response object with details of the added charging station.
     *
     * @PostMapping("/add")
     */
    @PostMapping("/add")
    public StationForAdminResponseDTO add(@Valid @RequestBody ChargingStation chargingStation) {
        return chargingStationService.add(chargingStation);
    }

    @GetMapping("/{id}")
    public StationForAdminResponseDTO findById(@PathVariable("id") Long id) throws NotFoundInDatabaseException {
        return chargingStationService.findById(id);
    }

    /**
     * This method is used to retrieve all charging stations.
     * It returns a ListStationsForAdminResponseDTO object which contains a list of StationForAdminResponseDTO objects.
     *
     * @return ListStationsForAdminResponseDTO This returns the response object with a list of all charging stations.
     *
     * @GetMapping
     */
    @GetMapping
    public ListStationsForAdminResponseDTO findAll() {
        List<StationForAdminResponseDTO> stations = chargingStationService.findAll();

        return new ListStationsForAdminResponseDTO(stations);
    }
}
