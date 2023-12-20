package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Rent Response. It holds the most important information about rent, while keeping sensitive
 * information out of reach of any user. This was the reason for its implementation. It is used as response.
 * The class is annotated with @Getter, @Setter, @NoArgsConstructor, @AllArgsConstructor, and @Data
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, toString methods, and constructors.
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
