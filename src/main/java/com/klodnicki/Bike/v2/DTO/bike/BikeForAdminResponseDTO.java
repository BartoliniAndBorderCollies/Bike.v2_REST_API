package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;

import java.time.LocalDateTime;

public class BikeForAdminResponseDTO {

    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
    private GpsCoordinates gpsCoordinates;

    public BikeForAdminResponseDTO(String serialNumber, boolean isRented, BikeType bikeType, LocalDateTime
            rentalStartTime, LocalDateTime rentalEndTime, double amountToBePaid, GpsCoordinates gpsCoordinates) {
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
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

    public BikeType getBikeType() {
        return bikeType;
    }

    public void setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
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

    public GpsCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(GpsCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }
}