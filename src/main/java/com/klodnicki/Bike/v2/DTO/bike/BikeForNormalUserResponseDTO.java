package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import lombok.*;

import java.time.LocalDateTime;

/**
 * This class is used to wrap {@link com.klodnicki.Bike.v2.model.entity.Bike Bike class} as DTO, and it is used only as
 * a response in application REST API. This response object is dedicated to be visible for normal users without ROLE_ADMIN.
 * It contains basic data meant to be available to users without this role.
 * <br>
 * In comparison to {@link BikeForAdminResponseDTO} this class holds less fields, just the basic information visible for
 * normal users.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
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
