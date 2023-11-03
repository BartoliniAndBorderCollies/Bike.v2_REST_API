package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.RentableVehicle;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
//@DiscriminatorValue("bike")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bike extends RentableVehicle {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bike bike = (Bike) o;
        return Objects.equals(id, bike.id) && bikeType == bike.bikeType && Objects.equals(rent, bike.rent) && Objects.equals(user, bike.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bikeType, rent, user);
    }
}
