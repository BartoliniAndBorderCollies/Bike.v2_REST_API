package com.klodnicki.Bike.v2.DTO.user;

import com.klodnicki.Bike.v2.DTO.authority.AuthorityDTO;
import com.klodnicki.Bike.v2.model.entity.Authority;
import lombok.*;

import java.util.Set;

/**
 * A data transfer object (DTO) class that encapsulates the user data for a normal user.
 * This class was designed to be a response for normal user with basic information about specific user.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserForNormalUserResponseDTO {

    private Long id;
    private String name;
    private boolean isAccountValid;
    private Set<AuthorityDTO> authorities;
}
