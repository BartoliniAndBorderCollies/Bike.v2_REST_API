package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;

import java.util.List;

public interface RentBikeGenericService extends GenericService<BikeForNormalUserResponseDTO, BikeRequestDTO>{

    List<BikeForNormalUserResponseDTO> findAvailableBikes();

    BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id);

}
