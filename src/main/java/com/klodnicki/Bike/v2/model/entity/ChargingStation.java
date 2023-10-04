package com.klodnicki.Bike.v2.model.entity;

import jakarta.persistence.*;

@Entity
public class ChargingStation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;

    private int freeSlots;

    public ChargingStation(Long id, String name, int freeSlots) {
        this.id = id;
        this.name = name;
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
}
