package com.klodnicki.Bike.v2.DTO.user;

import com.klodnicki.Bike.v2.model.entity.Authority;
import lombok.*;

import java.util.Set;

/**
 * A data transfer object (DTO) class that encapsulates the user data for an admin.
 * This class is used to transfer data between different parts of the application.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
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
