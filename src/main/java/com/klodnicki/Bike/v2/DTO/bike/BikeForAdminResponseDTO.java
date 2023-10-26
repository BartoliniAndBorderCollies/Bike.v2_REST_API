package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.DTO.station.StationForAdminResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForAdminResponseDTO;
import com.klodnicki.Bike.v2.model.BikeType;
import com.klodnicki.Bike.v2.model.GpsCoordinates;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BikeForAdminResponseDTO {
    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private double amountToBePaid;
    private GpsCoordinates gpsCoordinates;
    private UserForAdminResponseDTO userForAdminResponseDTO;
    private StationForAdminResponseDTO stationForAdminResponseDTO;
}
