package com.klodnicki.Bike.v2.DTO.user;

import com.klodnicki.Bike.v2.DTO.authority.AuthorityDTO;
import com.klodnicki.Bike.v2.model.entity.Authority;
import lombok.*;

import java.util.Set;

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
