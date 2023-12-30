package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Rent Rent class} as DTO, and it is used only as
 * a request in application REST API in {@link com.klodnicki.Bike.v2.rest.controller.RentBikeController RentBikeController}
 * during un update rent procedure.
 * This request object is dedicated to be visible for all kind of users, therefore it holds just the basic information about rent.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentRequestDTO {

    @NotNull(message = "Id must not be null")
    private Long id;
    @PastOrPresent(message = "Rental start time must be present or past")
    private LocalDateTime rentalStartTime;
    @FutureOrPresent(message = "Rental end time must be present or future")
    private LocalDateTime rentalEndTime;
    @PositiveOrZero(message = "Must be positive or zero")
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
