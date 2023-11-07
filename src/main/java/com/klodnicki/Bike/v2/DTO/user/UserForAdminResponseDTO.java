package com.klodnicki.Bike.v2.DTO.user;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserForAdminResponseDTO {

    private Long id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private int accountNumber;
    private boolean isAccountValid;
    private String role;
    private double balance;
}
