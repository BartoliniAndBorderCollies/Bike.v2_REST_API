package com.klodnicki.Bike.v2.DTO.user;

public class UserForNormalUserResponseDTO {

    private Long id;
    private String name;
    private boolean isAccountValid;
    private String role;

    public UserForNormalUserResponseDTO(Long id, String name, boolean isAccountValid, String role) {
        this.id = id;
        this.name = name;
        this.isAccountValid = isAccountValid;
        this.role = role;
    }

    public UserForNormalUserResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAccountValid() {
        return isAccountValid;
    }

    public void setAccountValid(boolean accountValid) {
        isAccountValid = accountValid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
