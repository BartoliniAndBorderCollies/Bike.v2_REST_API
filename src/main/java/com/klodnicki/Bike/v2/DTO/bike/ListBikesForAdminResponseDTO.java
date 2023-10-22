package com.klodnicki.Bike.v2.DTO.bike;

import java.util.List;

public class ListBikesForAdminResponseDTO {

    private List<BikeForAdminResponseDTO> dtosList;

    public ListBikesForAdminResponseDTO(List<BikeForAdminResponseDTO> dtosList) {
        this.dtosList = dtosList;
    }

    public List<BikeForAdminResponseDTO> getDtosList() {
        return dtosList;
    }

    public void setDtosList(List<BikeForAdminResponseDTO> dtosList) {
        this.dtosList = dtosList;
    }
}
