package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.api.operation.AddOperation;
import com.klodnicki.Bike.v2.service.api.operation.FindOperation;
import com.klodnicki.Bike.v2.service.api.operation.SaveOperation;

/**
 * This interface defines the operations for the ChargingStationService.
 * It extends AddOperation, FindOperation, and SaveOperation interfaces.
 */
public interface ChargingStationServiceApi extends AddOperation<StationForAdminResponseDTO, ChargingStation>,
        FindOperation<StationForAdminResponseDTO, Long>, SaveOperation<ChargingStation> {
    ChargingStation findStationById(Long id) throws NotFoundInDatabaseException;

    /**
     * This method is used to add a bike to a charging station's list.
     * @param chargingStationId This is the ID of the charging station where the bike will be added.
     * @param bikeId This is the ID of the bike to be added to the charging station's list.
     * @return ChargingStation This returns the updated charging station object after the bike has been added.
     */
    ChargingStation addBikeToList(Long chargingStationId, Long bikeId) throws NotFoundInDatabaseException;
}
