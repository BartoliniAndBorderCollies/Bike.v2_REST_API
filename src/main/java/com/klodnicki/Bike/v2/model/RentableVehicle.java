package com.klodnicki.Bike.v2.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass // ta klasa nie może być jednocześnie encją
//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) - te dwie adnotacje zrobiłyby, że jest jedna tabela ze wszystkimi
// kolumnami
//@DiscriminatorColumn(name="vehicle_type", discriminatorType = DiscriminatorType.STRING)

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class RentableVehicle {

    private String serialNumber;
    private boolean isRented;
    private double amountToBePaid;
    @Embedded
    private GpsCoordinates gpsCoordinates;

    public RentableVehicle(String serialNumber, boolean isRented, double amountToBePaid, GpsCoordinates gpsCoordinates) {
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
    }

    public RentableVehicle() {
    }
}
