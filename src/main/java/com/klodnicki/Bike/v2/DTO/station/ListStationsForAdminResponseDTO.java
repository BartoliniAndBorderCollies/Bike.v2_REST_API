package com.klodnicki.Bike.v2.DTO.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor

public class ListStationsForAdminResponseDTO {

    private List<StationForAdminResponseDTO> listOfStationsDTOs;
}
