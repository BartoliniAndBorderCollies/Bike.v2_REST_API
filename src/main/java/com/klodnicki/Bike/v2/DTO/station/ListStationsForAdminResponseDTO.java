package com.klodnicki.Bike.v2.DTO.station;

import java.util.List;

public class ListStationsForAdminResponseDTO {

    private List<StationForAdminResponseDTO> listOfStationsDTOs;

    public ListStationsForAdminResponseDTO(List<StationForAdminResponseDTO> listOfStationsDTOs) {
        this.listOfStationsDTOs = listOfStationsDTOs;
    }

    public List<StationForAdminResponseDTO> getListOfStationsDTOs() {
        return listOfStationsDTOs;
    }

    public void setListOfStationsDTOs(List<StationForAdminResponseDTO> listOfStationsDTOs) {
        this.listOfStationsDTOs = listOfStationsDTOs;
    }
}
