package com.klodnicki.Bike.v2.service.api;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.RentRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * This interface defines the operations for the RentBikeService.
 * It extends the RentServiceApi interface.
 */
public interface RentBikeServiceApi extends RentServiceApi<RentResponseDTO, ResponseEntity<?>, RentRequest,
        Long, Long> {

    List<BikeForNormalUserResponseDTO> findAvailableBikes();
    BikeForNormalUserResponseDTO findBikeForNormalUserById(Long id) throws NotFoundInDatabaseException;
    /**
     * This method is used to update a rent.
     * @param id This is the ID of the rent to be updated.
     * @param rentRequestDTO This is a request object which contains the updated details of the rent.
     * @return RentResponseDTO This returns the response object with details of the updated rent.
     * TODO: add throws
     */
    RentResponseDTO updateRent(Long id, RentRequestDTO rentRequestDTO) throws NotFoundInDatabaseException;
}
