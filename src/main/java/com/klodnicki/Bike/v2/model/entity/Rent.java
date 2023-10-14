package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;

@Entity
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
//Rent class is a child class - or no-owning side - therefore it contains FK, therefore it joins columns
    @OneToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "chargingStation_id")
    private ChargingStation chargingStation;

    public Rent(Long id, Bike bike, User user, ChargingStation chargingStation) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.chargingStation = chargingStation;
    }

    public Rent() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
