package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Rent Response. This class is used to transfer data between
 * different parts of the application. It includes various fields related to a rent response.
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
