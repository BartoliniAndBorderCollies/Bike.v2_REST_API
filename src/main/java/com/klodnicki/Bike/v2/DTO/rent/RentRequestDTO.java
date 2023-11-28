package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentRequestDTO {

    @NotNull(message = "Id must not be null")
    private Long id;
    @PastOrPresent(message = "Rental start time must be present or past")
    private LocalDateTime rentalStartTime;
    @FutureOrPresent(message = "Rental end time must be present or future")
    private LocalDateTime rentalEndTime;
    @PositiveOrZero(message = "Must be positive or zero")
    private int daysOfRent;
    private BikeForNormalUserResponseDTO bikeForNormalUserResponseDTO;
    private UserForNormalUserResponseDTO userForNormalUserResponseDTO;
    private StationForNormalUserResponseDTO stationForNormalUserResponseDTO;
}
