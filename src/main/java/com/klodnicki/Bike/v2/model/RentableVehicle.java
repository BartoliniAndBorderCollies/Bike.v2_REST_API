package com.klodnicki.Bike.v2.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@MappedSuperclass // ta klasa nie może być jednocześnie encją
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) - te dwie adnotacje zrobiłyby, że jest jedna tabela ze wszystkimi
// kolumnami
//@DiscriminatorColumn(name="vehicle_type", discriminatorType = DiscriminatorType.STRING)

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
public abstract class RentableVehicle {

    private String serialNumber;
    private boolean isRented;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
    @Embedded
    private GpsCoordinates gpsCoordinates;

    public RentableVehicle(String serialNumber, boolean isRented, LocalDateTime rentalStartTime,
                           LocalDateTime rentalEndTime, double amountToBePaid, GpsCoordinates gpsCoordinates) {
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

    public GpsCoordinates getGpsCoordinates() {
        return gpsCoordinates;
    }

    public void setGpsCoordinates(GpsCoordinates gpsCoordinates) {
        this.gpsCoordinates = gpsCoordinates;
    }
}
