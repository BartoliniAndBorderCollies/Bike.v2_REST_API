package com.klodnicki.Bike.v2.DTO.user;

public class UserForNormalUserResponseDTO {

    private String name;

    public UserForNormalUserResponseDTO(String name) {
        this.name = name;
    }

    public UserForNormalUserResponseDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
