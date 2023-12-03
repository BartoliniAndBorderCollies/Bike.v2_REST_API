package com.klodnicki.Bike.v2.DTO.user;

import com.klodnicki.Bike.v2.model.entity.Authority;
import lombok.*;

import java.util.Set;

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
    private Set<Authority> authorities;
}
