package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
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
    private UserForAdminResponseDTO userForAdminResponseDTO;
    private StationForAdminResponseDTO stationForAdminResponseDTO;

    public BikeRequestDTO(Long id, String serialNumber, boolean isRented, BikeType bikeType, double amountToBePaid,
                          GpsCoordinates gpsCoordinates, UserForAdminResponseDTO userForAdminResponseDTO,
                          StationForAdminResponseDTO stationForAdminResponseDTO) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
        this.amountToBePaid = amountToBePaid;
        this.gpsCoordinates = gpsCoordinates;
        this.userForAdminResponseDTO = userForAdminResponseDTO;
        this.stationForAdminResponseDTO = stationForAdminResponseDTO;
    }
}