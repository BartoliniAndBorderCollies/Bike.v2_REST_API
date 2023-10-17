package com.klodnicki.Bike.v2.model;

import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;

public class RentRequest {

    private Long userId;
    private Long bikeId;

    private Long chargingStationId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getBikeId() {
        return bikeId;
    }

    public void setBikeId(Long bikeId) {
        this.bikeId = bikeId;
    }

    public Long getChargingStationId() {
        return chargingStationId;
    }

    public void setChargingStationId(Long chargingStationId) {
        this.chargingStationId = chargingStationId;
    }
}
