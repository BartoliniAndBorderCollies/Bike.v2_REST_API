package com.klodnicki.Bike.v2.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersForAdminResponseDTO {

    private List<UserForAdminResponseDTO> listOfUsersDTOs;
}
