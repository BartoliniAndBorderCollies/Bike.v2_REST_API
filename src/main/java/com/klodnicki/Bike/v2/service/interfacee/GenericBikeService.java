package com.klodnicki.Bike.v2.service.interfacee;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.service.interfacee.basic.operation.*;

import java.util.List;

public interface GenericBikeService extends AddService<BikeForAdminResponseDTO, BikeRequestDTO>,
        FindService<BikeForAdminResponseDTO, Long>,
        UpdateService<BikeForAdminResponseDTO, Long, BikeRequestDTO>,
        DeleteService<Long>,
        SaveService<Bike> {

    List<Bike> findByIsRentedFalse();
    Bike findBikeById(Long id);
}
