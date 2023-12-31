package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Rent Rent class} as DTO, and it is used only as
 * a response in application REST API in {@link com.klodnicki.Bike.v2.rest.controller.RentBikeController RentBikeController}
 * during rent a vehicle and rent update procedures.
 * This response object is dedicated to be visible for all kind of users, therefore it holds just the basic information about rent,
 *  while keeping sensitive information out of reach of any user.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentResponseDTO {

    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    /**
     * Bike information in the context of normal user operations.
     */
    private BikeForNormalUserResponseDTO bikeForNormalUserResponseDTO;
    /**
     * User information in the context of normal user operations.
     */
    private UserForNormalUserResponseDTO userForNormalUserResponseDTO;
    /**
     * Charging station information in the context of normal user operations.
     */
    private StationForNormalUserResponseDTO stationForNormalUserResponseDTO;
}
