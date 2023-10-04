package com.klodnicki.Bike.v2.model.entity;

import com.klodnicki.Bike.v2.model.BikeType;
import jakarta.persistence.*;

@Entity
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String serialNumber;

    private boolean isRented;

    private BikeType bikeType;

    public Bike(Long id, String serialNumber, boolean isRented, BikeType bikeType) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
    }

    public Bike() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
}
