package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;

@Entity
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String address;
    private String city;
    private int freeSlots;

    //ChargingStation is a parent class (owning side) of the relation
    @OneToOne(mappedBy = "chargingStation")
    private Rent rent;

    public ChargingStation(Long id, String name, String address, String city, int freeSlots) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.freeSlots = freeSlots;
    }

    public ChargingStation() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Rent getRent() {
        return rent;
    }

    public void setRent(Rent rent) {
        this.rent = rent;
    }
}
