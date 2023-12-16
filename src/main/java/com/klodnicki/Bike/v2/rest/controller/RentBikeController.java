package com.klodnicki.Bike.v2.rest.controller;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.bike.ListBikesForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentRequestDTO;
import com.klodnicki.Bike.v2.DTO.rent.RentResponseDTO;
import com.klodnicki.Bike.v2.exception.NotFoundInDatabaseException;
import com.klodnicki.Bike.v2.model.RentRequest;
import com.klodnicki.Bike.v2.service.api.RentBikeServiceApi;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class RentBikeController {

    private final RentBikeServiceApi rentBikeService;

    /**
     * This method is used to retrieve all available bikes.
     * Available bike is a bike which has isRented status set as false.
     * The method returns a ListBikesForNormalUserResponseDTO object which contains a list of BikeForNormalUserResponseDTO objects.
     *
     * @return ListBikesForNormalUserResponseDTO This returns the response object with a list of all available bikes.
     *
     * @GetMapping("/bikes")
     */
    @GetMapping("/bikes")
    public ListBikesForNormalUserResponseDTO findAvailableBikes() {
        List<BikeForNormalUserResponseDTO> bikeDTOs = rentBikeService.findAvailableBikes();

        return new ListBikesForNormalUserResponseDTO(bikeDTOs);
    }

    @GetMapping("/bikes/{id}")
    public BikeForNormalUserResponseDTO findBikeForNormalUserById(@PathVariable Long id) throws NotFoundInDatabaseException {
        return rentBikeService.findBikeForNormalUserById(id);
    }
    /**
     * This method is used to rent a bike.
     * It takes a RentRequest object as input and returns a RentResponseDTO object.
     * The RentRequest must fulfil some requirements since it is preceded by @Valid.
     *
     * @param rentRequest This is a request object which contains the details of the bike to be rented.
     * @return RentResponseDTO This returns the response object with details of the rented bike.
     * @throws NotFoundInDatabaseException if a bike, user or charging station is not found in database.
     * @PostMapping("/rentals/add")
     */
    @PostMapping("/rentals/add")
    public RentResponseDTO rentBike(@Valid @RequestBody RentRequest rentRequest) throws NotFoundInDatabaseException {
        return rentBikeService.rent(rentRequest);
    }
    /**
     * This method is used to update a rent.
     * It takes a RentRequestDTO object and an ID of Rent as input and returns a RentResponseDTO object.
     * The RentRequest must fulfil some requirements since it is preceded by @Valid.
     *
     * @param id This is the ID of the rent to be updated.
     * @param rentRequestDTO This is a request object which contains the updated details of the rent.
     * @return RentResponseDTO This returns the response object with details of the updated rent.
     * @throws NotFoundInDatabaseException if rent is not found in database.
     * @PutMapping("/rentals/{id}")
     */
    @PutMapping("/rentals/{id}")
    public RentResponseDTO updateRent(@PathVariable Long id, @Valid @RequestBody RentRequestDTO rentRequestDTO)
            throws NotFoundInDatabaseException {
        return rentBikeService.updateRent(id, rentRequestDTO);
    }

    /**
     * This method is used to return a rented bike.
     * It takes a rent ID and a return charging station ID as input and returns a ResponseEntity object.
     *
     * @param rentId This is the ID of the rent to be returned.
     * @param returnChargingStationId This is the ID of the charging station where the bike will be returned.
     * @return ResponseEntity This returns the response entity after the bike has been returned.
     * @throws NotFoundInDatabaseException if rent, bike, user or charging station is not found in database.
     * @PutMapping("/returns/{rentId}")
     */
    @PutMapping("/returns/{rentId}")
    public ResponseEntity<?> returnBike(@PathVariable Long rentId, @RequestParam Long returnChargingStationId)
            throws NotFoundInDatabaseException {
        return rentBikeService.returnVehicle(rentId, returnChargingStationId);
    }
}
