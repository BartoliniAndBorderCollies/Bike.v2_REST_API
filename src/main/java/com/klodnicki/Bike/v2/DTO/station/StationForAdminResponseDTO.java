package com.klodnicki.Bike.v2.DTO.station;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StationForAdminResponseDTO {

    private Long id;
    private String name;
    private String address;
    private String city;
    private int freeSlots;
}
