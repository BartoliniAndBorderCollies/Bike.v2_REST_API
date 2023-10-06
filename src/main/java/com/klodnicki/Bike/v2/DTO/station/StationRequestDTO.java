package com.klodnicki.Bike.v2.DTO.station;

public class StationRequestDTO {


    private Long id;
    private String name;
    private String address;
    private String city;
    private int freeSlots;

    public StationRequestDTO(Long id, String name, String address, String city, int freeSlots) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.city = city;
        this.freeSlots = freeSlots;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }
}
