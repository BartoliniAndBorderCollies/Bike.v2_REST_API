package com.klodnicki.Bike.v2.DTO.bike;

import java.util.List;

public class ListBikesForAdminResponseDTO {

    private List<BikeForAdminResponseDTO> bikesListDTOs;

    public ListBikesForAdminResponseDTO(List<BikeForAdminResponseDTO> bikesListDTOs) {
        this.bikesListDTOs = bikesListDTOs;
    }

    public List<BikeForAdminResponseDTO> getBikesListDTOs() {
        return bikesListDTOs;
    }

    public void setBikesListDTOs(List<BikeForAdminResponseDTO> bikesListDTOs) {
        this.bikesListDTOs = bikesListDTOs;
    }
}
