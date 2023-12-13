package com.klodnicki.Bike.v2.DTO.user;

import jakarta.persistence.Transient;
import lombok.*;

import java.util.Objects;

/**
 * A data transfer object (DTO) class that encapsulates the user request data.
 * This class is used to transfer data between different parts of the application.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
@RequiredArgsConstructor
@Data
public class UserRequestDTO {
    private Long id;
    private final String name;
    private final String phoneNumber;
    private String emailAddress;
    private int accountNumber;
    private boolean isAccountValid;
    private String role;
    private double balance;
}
