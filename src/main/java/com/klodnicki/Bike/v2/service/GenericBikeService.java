package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;

import java.util.List;

public interface GenericBikeService extends GenericService<BikeForAdminResponseDTO, BikeRequestDTO> {

    Bike findBikeById(Long id);

    List<Bike> findByIsRentedFalse();

    Bike save(Bike bike);
}
