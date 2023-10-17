package com.klodnicki.Bike.v2.DTO.user;

import jakarta.persistence.Transient;
import lombok.*;

import java.util.Objects;

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
