package com.klodnicki.Bike.v2.DTO.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for a list of Bike information in the context of normal user operations.
 * This class is used to transfer data between different parts of the application.
 * It includes a list of BikeForNormalUserResponseDTO objects.
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
