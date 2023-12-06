package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.api.operation.AddOperation;
import com.klodnicki.Bike.v2.service.api.operation.FindOperation;
import com.klodnicki.Bike.v2.service.api.operation.SaveOperation;

public interface ChargingStationServiceApi extends AddOperation<StationForAdminResponseDTO, ChargingStation>,
        FindOperation<StationForAdminResponseDTO, Long>, SaveOperation<ChargingStation> {
    ChargingStation findStationById(Long id) throws NotFoundInDatabaseException;

    ChargingStation addBikeToList(Long chargingStationId, Long bikeId) throws NotFoundInDatabaseException;
}
