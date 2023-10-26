package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    @OneToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "chargingStation_id")
    private ChargingStation chargingStation;

    public Rent(LocalDateTime rentalStartTime, LocalDateTime rentalEndTime, Bike bike, User user,
                ChargingStation chargingStation, int daysOfRent) {
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.bike = bike;
        this.user = user;
        this.chargingStation = chargingStation;
        this.daysOfRent = daysOfRent;
    }
}
