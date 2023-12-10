package com.klodnicki.Bike.v2.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


//@Entity
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE) - te dwie adnotacje zrobiłyby, że jest jedna tabela ze wszystkimi
// kolumnami
//@DiscriminatorColumn(name="vehicle_type", discriminatorType = DiscriminatorType.STRING)

//@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
/**
 * Represents a vehicle that can be rented.
 * This is an abstract class and cannot be instantiated directly.
 * Concrete subclasses of this class represent specific types of rentable vehicles.
 */
@MappedSuperclass // ta klasa nie może być jednocześnie encją
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class RentableVehicle {

    private String serialNumber;
    private boolean isRented;
    @Embedded
    private GpsCoordinates gpsCoordinates;
}
