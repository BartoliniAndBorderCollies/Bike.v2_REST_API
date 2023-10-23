package com.klodnicki.Bike.v2.DTO.rent;

import com.klodnicki.Bike.v2.DTO.bike.BikeForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.station.StationForNormalUserResponseDTO;
import com.klodnicki.Bike.v2.DTO.user.UserForNormalUserResponseDTO;

import java.time.LocalDateTime;

public class RentRequestDTO {

    private Long id;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;
    private int daysOfRent;
    private BikeForNormalUserResponseDTO bikeForNormalUserResponseDTO;
    private UserForNormalUserResponseDTO userForNormalUserResponseDTO;
    private StationForNormalUserResponseDTO stationForNormalUserResponseDTO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getRentalStartTime() {
        return rentalStartTime;
    }

    public void setRentalStartTime(LocalDateTime rentalStartTime) {
        this.rentalStartTime = rentalStartTime;
    }

    public LocalDateTime getRentalEndTime() {
        return rentalEndTime;
    }

    public void setRentalEndTime(LocalDateTime rentalEndTime) {
        this.rentalEndTime = rentalEndTime;
    }

    public int getDaysOfRent() {
        return daysOfRent;
    }

    public void setDaysOfRent(int daysOfRent) {
        this.daysOfRent = daysOfRent;
    }

    public BikeForNormalUserResponseDTO getBikeForNormalUserResponseDTO() {
        return bikeForNormalUserResponseDTO;
    }

    public void setBikeForNormalUserResponseDTO(BikeForNormalUserResponseDTO bikeForNormalUserResponseDTO) {
        this.bikeForNormalUserResponseDTO = bikeForNormalUserResponseDTO;
    }

    public UserForNormalUserResponseDTO getUserForNormalUserResponseDTO() {
        return userForNormalUserResponseDTO;
    }

    public void setUserForNormalUserResponseDTO(UserForNormalUserResponseDTO userForNormalUserResponseDTO) {
        this.userForNormalUserResponseDTO = userForNormalUserResponseDTO;
    }

    public StationForNormalUserResponseDTO getStationForNormalUserResponseDTO() {
        return stationForNormalUserResponseDTO;
    }

    public void setStationForNormalUserResponseDTO(StationForNormalUserResponseDTO stationForNormalUserResponseDTO) {
        this.stationForNormalUserResponseDTO = stationForNormalUserResponseDTO;
    }
}
