package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Getter
@Setter
@NoArgsConstructor
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
    @OneToOne(mappedBy = "chargingStation", cascade = CascadeType.ALL)
    private Rent rent;

    @OneToMany(mappedBy = "chargingStation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Bike> bikeList;

    public ChargingStation(Long id, String name, String address, String city, int freeSlots, List<Bike> bikeList) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.freeSlots = freeSlots;
        this.bikeList = bikeList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o != null && getClass() == o.getClass()) {
            ChargingStation that = (ChargingStation) o;
            if (freeSlots == that.freeSlots &&
                    Objects.equals(id, that.id) &&
                    Objects.equals(name, that.name) &&
                    Objects.equals(address, that.address) &&
                    Objects.equals(city, that.city) &&
                    Objects.equals(rent, that.rent)) {

                // Compare bikeList as sets
                Set<Bike> thisBikeSet = bikeList == null ? new HashSet<>() : new HashSet<>(bikeList);
                Set<Bike> thatBikeSet = that.bikeList == null ? new HashSet<>() : new HashSet<>(that.bikeList);
                return thisBikeSet.equals(thatBikeSet);
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        Set<Bike> bikeSet = new HashSet<>(bikeList);
        return Objects.hash(id, name, address, city, freeSlots, rent, bikeSet);
    }
}
