package com.klodnicki.Bike.v2.DTO.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for a list of Bike information in the context of normal user operations.
 * This was created to hold the list of Bike DTOs in one object. The content of the information is intended for normal user.
 * It includes a list of BikeForNormalUserResponseDTO objects. It can be used both: as response and request.
 * The class is annotated with @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBikesForNormalUserResponseDTO {

    /**
     * List of BikeForNormalUserResponseDTO objects. Each object in the list represents
     * information about a bike in the context of normal user operations.
     */
    private List<BikeForNormalUserResponseDTO> listOfBikesDTOs;
}
