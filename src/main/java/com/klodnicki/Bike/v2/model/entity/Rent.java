package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
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

    public Rent(LocalDateTime rentalStartTime, LocalDateTime rentalEndTime, Bike bike, User user, ChargingStation chargingStation) {
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.bike = bike;
        this.user = user;
        this.chargingStation = chargingStation;
    }

    public Rent() {
    }
}
