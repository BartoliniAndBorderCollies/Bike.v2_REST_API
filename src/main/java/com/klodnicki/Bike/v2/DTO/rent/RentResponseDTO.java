package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.model.entity.Bike;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;

import java.time.LocalDateTime;

public class RentResponseDTO {

    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    private Bike bike;
    private User user;
    private ChargingStation chargingStation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public LocalDateTime getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public int getDaysOfRent() {
        return daysOfRent;
    }

    public void setDaysOfRent(int daysOfRent) {
        this.daysOfRent = daysOfRent;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChargingStation getChargingStation() {
        return chargingStation;
    }

    public void setChargingStation(ChargingStation chargingStation) {
        this.chargingStation = chargingStation;
    }
}
