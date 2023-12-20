package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Bike information in the context of Admin operations. Since it is intended for Admin use, it holds
 * more details than a typical DTO for a normal user. It is used as response.
 * This class is annotated with @Data, @Builder, @AllArgsConstructor, and @NoArgsConstructor from the Lombok library to
 * automatically generate boilerplate code like getters, setters, equals, hashcode, and toString methods,
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
