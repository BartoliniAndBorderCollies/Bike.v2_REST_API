package com.klodnicki.Bike.v2.DTO.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to hold the list of {@link BikeForAdminResponseDTO} in one object. It can be used both as response and request in REST API.
 * The content of the information kept in this object is dedicated for users with ROLE_ADMIN only.
 * <br>
 * This class has annotations from the Lombok library to automatically generate boilerplate code like getters, setters,
 * equals, hashcode, and toString methods,
 * a builder pattern, and constructors with no arguments and all arguments.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListBikesForAdminResponseDTO {

    /**
     * List of BikeForAdminResponseDTO objects. Each object in the list represents
     * information about a bike in the context of Admin operations.
     */
    private List<BikeForAdminResponseDTO> bikesListDTOs;
}
