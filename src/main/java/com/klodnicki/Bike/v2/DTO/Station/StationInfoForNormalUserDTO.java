package com.klodnicki.Bike.v2.DTO.Station;

public class StationInfoForNormalUserDTO {

    private String name;

    private int freeSlots;

    public StationInfoForNormalUserDTO(String name, int freeSlots) {
        this.name = name;
        this.freeSlots = freeSlots;
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
