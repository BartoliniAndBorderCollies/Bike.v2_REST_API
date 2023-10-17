package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import com.klodnicki.Bike.v2.model.entity.ChargingStation;
import com.klodnicki.Bike.v2.model.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BikeForAdminResponseDTO {
    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
    private GpsCoordinates gpsCoordinates;
    private User user;
    private ChargingStation chargingStation;

    public BikeForAdminResponseDTO(Long id, String serialNumber, boolean isRented, BikeType bikeType,
                                   LocalDateTime rentalStartTime, LocalDateTime rentalEndTime, double amountToBePaid,
                                   GpsCoordinates gpsCoordinates, User user, ChargingStation chargingStation) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
        this.user = user;
        this.chargingStation = chargingStation;
    }

    public BikeForAdminResponseDTO() {
    }
}
