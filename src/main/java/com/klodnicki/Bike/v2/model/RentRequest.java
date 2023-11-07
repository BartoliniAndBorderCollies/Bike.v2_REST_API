package com.klodnicki.Bike.v2.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {

    private Long id;
    private Long userId;
    private Long bikeId;
    private int daysOfRent;
}
