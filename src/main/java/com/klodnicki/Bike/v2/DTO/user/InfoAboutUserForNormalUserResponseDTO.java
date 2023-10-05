package com.klodnicki.Bike.v2.DTO.user;

public class InfoAboutUserForNormalUserResponseDTO {

    private String name;

    public InfoAboutUserForNormalUserResponseDTO(String name) {
        this.name = name;
    }

    public InfoAboutUserForNormalUserResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
