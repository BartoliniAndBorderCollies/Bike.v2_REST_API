package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.bike.BikeForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.BikeRequestDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.service.api.operation.*;

import java.util.List;

/**
 * This interface defines the operations for the BikeService.
 * It extends AddOperation, FindOperation, UpdateOperation, DeleteOperation, and SaveOperation interfaces.
 */
public interface BikeServiceApi extends AddOperation<BikeForAdminResponseDTO, BikeRequestDTO>,
        FindOperation<BikeForAdminResponseDTO, Long>,
        UpdateOperation<BikeForAdminResponseDTO, Long, BikeRequestDTO>,
        DeleteOperation<Long>,
        SaveOperation<Bike> {

    /**
     * This method is used to find all bikes that are not rented.
     * @return List<Bike> This returns a list of bikes that are not rented.
     */
    List<Bike> findByIsRentedFalse();

    /**
     * This method is used to find a bike by its ID.
     * @param id This is the ID of the bike to be found.
     * @return Bike This returns the bike with the given ID.
     */
    Bike findBikeById(Long id) throws NotFoundInDatabaseException;
}
