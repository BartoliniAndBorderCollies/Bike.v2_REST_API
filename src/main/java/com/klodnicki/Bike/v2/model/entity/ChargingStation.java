package com.klodnicki.Bike.v2.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
}
