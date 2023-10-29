package com.klodnicki.Bike.v2.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserForNormalUserResponseDTO {

    private Long id;
    private String name;
    private boolean isAccountValid;
    private String role;
}
