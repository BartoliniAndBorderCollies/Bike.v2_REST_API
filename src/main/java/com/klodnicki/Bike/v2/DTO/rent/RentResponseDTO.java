package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RentResponseDTO {

    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    private BikeForNormalUserResponseDTO bikeForNormalUserResponseDTO;
    private UserForNormalUserResponseDTO userForNormalUserResponseDTO;
    private StationForNormalUserResponseDTO stationForNormalUserResponseDTO;
}
