package com.klodnicki.Bike.v2.DTO.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Station information in the context of normal user operations.
 * This is a response with information for normal user. It includes various fields related to a station.
 * The class is annotated with @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationForNormalUserResponseDTO {

    private String name;
    private String address;
    private String city;
    private int freeSlots;
}
