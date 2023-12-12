package com.klodnicki.Bike.v2.DTO.station;

import lombok.*;

/**
 * Data Transfer Object (DTO) for Station information in the context of Admin operations.
 * This class is used to transfer data between different parts of the application.
 * It includes various fields related to a station.
 * The class is annotated with @Data, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, toString methods, and constructors.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationForAdminResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private int freeSlots;
}
