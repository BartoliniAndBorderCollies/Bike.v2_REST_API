package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.RentableVehicle;
import com.klodnicki.Bike.v2.model.RentalAction;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@DiscriminatorValue("bike")
@Getter
@Setter
public class Bike extends RentableVehicle implements RentalAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    private BikeType bikeType;
    //Bike is a parent class (owning side) of the relation with User and ChargingStation
    //Bike is non-owning side of the relation with Rent

    //mam kaskadę obustronną. Każda zmiana w Rent wpływa na Bike, każda zmiana w Bike wpływa na Rent.
    @OneToOne (mappedBy = "bike", cascade = CascadeType.ALL)
    private Rent rent;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "charging_station_id")
    private ChargingStation chargingStation;

    public Bike(Long id, BikeType bikeType, String serialNumber, boolean isRented, LocalDateTime rentalStartTime, LocalDateTime rentalEndTime,
                double amountToBePaid, GpsCoordinates gpsCoordinates) {
        super(serialNumber, isRented, amountToBePaid, gpsCoordinates);
        this.id = id;
        this.bikeType = bikeType;
    }

    public Bike() {
    }

    @Override
    public void rent() {

    }

    @Override
    public void giveBack() {

    }
}
