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
 * Data Transfer Object (DTO) for Rent Request. It holds the most important information about rent, while keeping sensitive
 * information out of reach of any user. This was the reason for its implementation. It is used as request during an update rent procedure.
 * The class is annotated with @Getter, @Setter, @NoArgsConstructor, and @AllArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
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
