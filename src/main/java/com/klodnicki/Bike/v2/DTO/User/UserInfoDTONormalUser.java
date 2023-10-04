package com.klodnicki.Bike.v2.DTO.User;

public class UserInfoDTONormalUser {

    private String name;

    public UserInfoDTONormalUser(String name) {
        this.name = name;
    }

    public UserInfoDTONormalUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
