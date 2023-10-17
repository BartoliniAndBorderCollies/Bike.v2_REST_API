package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
//Rent class is a child class - or no-owning side - therefore it contains FK, therefore it joins columns
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "chargingStation_id")
    private ChargingStation chargingStation;

    public Rent(Bike bike, User user, ChargingStation chargingStation) {
        this.bike = bike;
        this.user = user;
        this.chargingStation = chargingStation;
    }

    public Rent() {
    }
}
