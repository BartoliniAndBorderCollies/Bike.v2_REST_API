package com.klodnicki.Bike.v2.DTO.bike;

import java.util.List;

public class ListBikesForNormalUserResponseDTO {

    private List<BikeForNormalUserResponseDTO> listOfBikesDTOs;

    public ListBikesForNormalUserResponseDTO(List<BikeForNormalUserResponseDTO> listOfBikesDTOs) {
        this.listOfBikesDTOs = listOfBikesDTOs;
    }

    public List<BikeForNormalUserResponseDTO> getListOfBikesDTOs() {
        return listOfBikesDTOs;
    }

    public void setListOfBikesDTOs(List<BikeForNormalUserResponseDTO> listOfBikesDTOs) {
        this.listOfBikesDTOs = listOfBikesDTOs;
    }
}
