package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * An entity class that represents a Rent in the system.
 * This class includes properties specific to a Rent.
 * It is annotated with JPA annotations to map it to the database.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    private double amountToBePaid;
    /**
     * The Bike associated with the Rent.
     * This is the owning side of the relation with Bike.
     */
    @OneToOne
    @JoinColumn(name = "bike_id")
    private Bike bike;

    /**
     * The User associated with the Rent.
     * This is the owning side of the relation with User.
     */
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * The ChargingStation associated with the Rent.
     * This is the owning side of the relation with ChargingStation.
     */
    @OneToOne
    @JoinColumn(name = "chargingStation_id")
    private ChargingStation chargingStation;
}
