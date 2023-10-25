package com.klodnicki.Bike.v2.service.serviceInterface;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenericRentBikeService extends RentService<RentResponseDTO, RentRequest>,
        ReturnService<ResponseEntity<?>, Long, Long, Long> {

    List<BikeForNormalUserResponseDTO> findAvailableBikes();

    BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id);

    ChargingStation addBikeToList(Long chargingStationId, Bike bike);

    RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO);
}
