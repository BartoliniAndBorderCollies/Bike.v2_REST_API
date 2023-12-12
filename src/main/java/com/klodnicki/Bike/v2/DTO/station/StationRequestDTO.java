package com.klodnicki.Bike.v2.DTO.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) for Station Request. This class is used to transfer data between
 * different parts of the application. It includes various fields related to a station request.
 * The class is annotated with @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationRequestDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private int freeSlots;
}
