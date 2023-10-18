package com.klodnicki.Bike.v2.model;

import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;
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
