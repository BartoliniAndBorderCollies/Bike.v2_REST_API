package com.klodnicki.Bike.v2.service.interfacee;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.AddService;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.FindService;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.SaveService;

public interface GenericChargingStationService extends AddService<StationForAdminResponseDTO, ChargingStation>,
        FindService<StationForAdminResponseDTO, Long>, SaveService<ChargingStation> {
    ChargingStation findStationById(Long id);
}
