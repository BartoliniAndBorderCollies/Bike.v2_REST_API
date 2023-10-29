package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.service.api.operation.*;

import java.util.List;

public interface BikeServiceApi extends AddOperation<BikeForAdminResponseDTO, BikeRequestDTO>,
        FindOperation<BikeForAdminResponseDTO, Long>,
        UpdateOperation<BikeForAdminResponseDTO, Long, BikeRequestDTO>,
        DeleteOperation<Long>,
        SaveOperation<Bike> {

    List<Bike> findByIsRentedFalse();
    Bike findBikeById(Long id);
}
