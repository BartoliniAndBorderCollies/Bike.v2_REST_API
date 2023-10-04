package com.klodnicki.Bike.v2.DTO.Bike;

import com.klodnicki.Bike.v2.model.BikeType;

public class BikeInfoForAdminDTO {

    private String serialNumber;

    private boolean isRented;

    private BikeType bikeType;

    //will have more info about which user is renting and so on


    public BikeInfoForAdminDTO(String serialNumber, boolean isRented, BikeType bikeType) {
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public BikeType getBikeType() {
        return bikeType;
    }

    public void setBikeType(BikeType bikeType) {
        this.bikeType = bikeType;
    }
}
