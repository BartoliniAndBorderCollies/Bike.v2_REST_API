package com.klodnicki.Bike.v2.DTO.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to hold the list of {@link com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO} in one object.
 * It can be used both as response and request in REST API.
 * The content of the information kept in this object is intended for normal users without ROLE_ADMIN.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
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
