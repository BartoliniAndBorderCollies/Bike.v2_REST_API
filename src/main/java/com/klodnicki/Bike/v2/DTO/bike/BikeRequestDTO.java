package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Bike Bike class} as DTO, and it is used only
 * as a request in REST API, in several methods where Bike instance is required. It was designed to wrap Bike entity and hold just the most important data
 * so that sensitive information is not visible. It holds for example DTOs of User and Charging Station and not their entire entities.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BikeRequestDTO {

    @NotNull(message = "Id must not be null")
    private Long id;
    @NotBlank(message = "Serial number must not be blank")
    private String serialNumber;
    private boolean isRented;
    @NotNull(message = "Bike type must not be null")
    private BikeType bikeType;
    @PositiveOrZero(message = "Amount to be paid must be positive or zero")
    private double amountToBePaid;
    @NotNull(message = "GPS coordinates must not be null")
    private GpsCoordinates gpsCoordinates;
    /**
     * User information in the context of Admin operations.
     */
    private UserForAdminResponseDTO user;
    /**
     * Charging station information in the context of Admin operations.
     */
    private StationForAdminResponseDTO chargingStation;
}