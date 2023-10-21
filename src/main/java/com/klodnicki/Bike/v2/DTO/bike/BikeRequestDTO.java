package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BikeRequestDTO {

    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private double amountToBePaid;
    private GpsCoordinates gpsCoordinates;
    private User user;
    private ChargingStation chargingStation;

    public BikeRequestDTO(Long id, String serialNumber, boolean isRented, BikeType bikeType, double amountToBePaid, GpsCoordinates gpsCoordinates, User user,
                          ChargingStation chargingStation) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
        this.user = user;
        this.chargingStation = chargingStation;
    }
}