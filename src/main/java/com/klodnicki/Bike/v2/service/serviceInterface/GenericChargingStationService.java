package com.klodnicki.Bike.v2.service.serviceInterface;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;

public interface GenericChargingStationService extends AddService<StationForAdminResponseDTO, ChargingStation>,
        FindService<StationForAdminResponseDTO, Long>, SaveService<ChargingStation> {
    ChargingStation findStationById(Long id);
}
