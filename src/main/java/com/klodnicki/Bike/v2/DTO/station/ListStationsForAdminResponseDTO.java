package com.klodnicki.Bike.v2.DTO.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Data Transfer Object (DTO) for a list of Station information in the context of Admin operations.
 * This class was implemented to have just one object with a list of DTOs inside. The information from the list is for
 * Admin purpose. It is used as response.
 * It includes a list of StationForAdminResponseDTO objects.
 * The class is annotated with @Getter, @Setter, @AllArgsConstructor, and @NoArgsConstructor
 * from the Lombok library to automatically generate boilerplate code like getters, setters,
 * and constructors.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListStationsForAdminResponseDTO {

    /**
     * List of StationForAdminResponseDTO objects. Each object in the list represents
     * information about a station in the context of Admin operations.
     */
    private List<StationForAdminResponseDTO> listOfStationsDTOs;
}
