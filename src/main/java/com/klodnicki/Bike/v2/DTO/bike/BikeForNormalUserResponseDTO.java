package com.klodnicki.Bike.v2.DTO.bike;

import com.klodnicki.Bike.v2.model.BikeType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BikeForNormalUserResponseDTO {
    private Long id;
    private String serialNumber;
    private boolean isRented;
    private BikeType bikeType;
    private LocalDateTime rentalStartTime;
    private LocalDateTime rentalEndTime;

    public BikeForNormalUserResponseDTO(Long id, String serialNumber, boolean isRented, BikeType bikeType,
                                        LocalDateTime rentalStartTime, LocalDateTime rentalEndTime) {
        this.id = id;
        this.serialNumber = serialNumber;
        this.isRented = isRented;
        this.bikeType = bikeType;
        this.rentalStartTime = rentalStartTime;
        this.rentalEndTime = rentalEndTime;
    }

    public BikeForNormalUserResponseDTO() {
    }
}
