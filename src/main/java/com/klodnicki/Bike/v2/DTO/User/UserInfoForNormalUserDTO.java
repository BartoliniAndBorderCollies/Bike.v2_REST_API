package com.klodnicki.Bike.v2.DTO.User;

public class UserInfoForNormalUserDTO {

    private String name;

    public UserInfoForNormalUserDTO(String name) {
        this.name = name;
    }

    public UserInfoForNormalUserDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
