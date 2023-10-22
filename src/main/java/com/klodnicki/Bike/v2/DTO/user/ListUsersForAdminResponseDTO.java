package com.klodnicki.Bike.v2.DTO.user;

import java.util.List;

public class ListUsersForAdminResponseDTO {

    private List<UserForAdminResponseDTO> listOfUsersDTOs;

    public ListUsersForAdminResponseDTO(List<UserForAdminResponseDTO> listOfUsersDTOs) {
        this.listOfUsersDTOs = listOfUsersDTOs;
    }

    public List<UserForAdminResponseDTO> getListOfUsersDTOs() {
        return listOfUsersDTOs;
    }

    public void setListOfUsersDTOs(List<UserForAdminResponseDTO> listOfUsersDTOs) {
        this.listOfUsersDTOs = listOfUsersDTOs;
    }
}
