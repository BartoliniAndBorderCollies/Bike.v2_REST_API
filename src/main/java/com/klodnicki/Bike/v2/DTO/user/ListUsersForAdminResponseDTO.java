package com.klodnicki.Bike.v2.DTO.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * A data transfer object (DTO) class that encapsulates the response data for the operation of listing users for an admin.
 * This is a response. It was implemented to have just one object with a list of users inside. The information from the list
 * is intended for admin.
 * It is annotated with Lombok annotations to reduce boilerplate code.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ListUsersForAdminResponseDTO {

    /**
     * A list of UserForAdminResponseDTO objects.
     * Each UserForAdminResponseDTO object in this list represents a user's data that is to be displayed to an admin.
     * The data for each user is encapsulated in the UserForAdminResponseDTO object.
     */
    private List<UserForAdminResponseDTO> listOfUsersDTOs;
}
