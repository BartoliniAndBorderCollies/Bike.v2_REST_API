package com.klodnicki.Bike.v2.model;

import java.time.LocalDateTime;

public abstract class Vehicle {

    private String serialNumber;
    private boolean isRented;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
    private String gpsCoordinates;

}
