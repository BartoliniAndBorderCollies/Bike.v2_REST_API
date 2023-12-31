package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Bike Bike class} as DTO and it is used only as
 * a response in application REST API in {@link com.klodnicki.Bike.v2.rest.controller.AdminBikeController AdminBikeController}.
 * This response object is dedicated to be visible for users with ROLE_ADMIN
 * as it contains more sensitive data not meant to be available to users without this role.
 * <br>
 * In comparison to {@link BikeForNormalUserResponseDTO BikeForNormalUserResponseDTO} this class holds more additional
 * fields specifically for admins view.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeForAdminResponseDTO {
    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
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
