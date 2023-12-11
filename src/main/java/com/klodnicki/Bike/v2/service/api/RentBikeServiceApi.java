package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.RentRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RentBikeServiceApi extends RentServiceApi<RentResponseDTO, ResponseEntity<?>, RentRequest,
        Long, Long> {

    List<BikeForNormalUserResponseDTO> findAvailableBikes();
    BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id) throws NotFoundInDatabaseException;
    RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO) throws NotFoundInDatabaseException;
}
