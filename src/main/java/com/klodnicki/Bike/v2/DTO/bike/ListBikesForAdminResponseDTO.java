package com.klodnicki.Bike.v2.DTO.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for a list of Bike information in the context of Admin operations. This was created
 * to hold the list of Bike DTOs in one object. It can be used both: as response and request. The content of the information is intended for Admin.
 * The class is annotated with @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
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
