package com.klodnicki.Bike.v2.model;

import java.time.LocalDateTime;

public abstract class RentableVehicle {

    protected String serialNumber;
    protected boolean isRented;
    protected LocalDateTime rentalStartTime;
    protected LocalDateTime rentalEndTime;
    protected double amountToBePaid;
    protected String gpsCoordinates;

    public RentableVehicle(String serialNumber, boolean isRented, LocalDateTime rentalStartTime, LocalDateTime rentalEndTime,
                           double amountToBePaid, String gpsCoordinates) {
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
    }

    public RentableVehicle() {
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
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

    public double getAmountToBePaid() {
        return amountToBePaid;
    }

    public void setAmountToBePaid(double amountToBePaid) {
        this.amountToBePaid = amountToBePaid;
    }

    public String getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(String gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }
}
