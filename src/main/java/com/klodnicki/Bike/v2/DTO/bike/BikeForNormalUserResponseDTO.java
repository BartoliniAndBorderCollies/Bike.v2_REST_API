package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) for Bike information in the context of normal user operations.
 * These are the most primary information suitable for normal user. It is used as response.
 * This class is annotated with @Data, @Builder, @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor from Lombok library to
 * automatically generate boilerplate code like getters, setters, equals, hashcode and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BikeForNormalUserResponseDTO {
    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
}
