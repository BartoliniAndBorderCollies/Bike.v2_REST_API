package com.klodnicki.Bike.v2.service;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.Rent;

import java.util.List;

public interface RentBikeGenericService {

    List<BikeForNormalUserResponseDTO> findAvailableBikes();

    BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id);

    RentResponseDTO rentBike(RentRequest rentRequest);

    ChargingStation addBikeToList(Long chargingStationId, Bike bike);

    Rent updateRent(Long id, Rent rent);

    void returnBike(Long rentId, Long returnChargingStationId, Long bikeId);
}
