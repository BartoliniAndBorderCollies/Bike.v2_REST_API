package com.klodnicki.Bike.v2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RentRequest {

    private Long userId;
    private Long bikeId;
    private Long chargingStationId;
    private int daysOfRent;
}
