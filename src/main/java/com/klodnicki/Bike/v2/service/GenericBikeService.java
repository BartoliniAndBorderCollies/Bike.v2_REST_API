package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.model.entity.Bike;

import java.util.List;

public interface GenericBikeService {

    Bike findBikeById(Long id);

    List<Bike> findByIsRentedFalse();

    Bike save(Bike bike);

    BikeForAdminResponseDTO add(BikeRequestDTO bikeDTO);

    BikeForAdminResponseDTO findById(Long id);

    List<BikeForAdminResponseDTO> findAll();

    void deleteById(Long id);

    BikeForAdminResponseDTO update(Long id, BikeRequestDTO bikeDTO);
}
