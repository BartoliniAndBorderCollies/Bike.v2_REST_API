package com.klodnicki.Bike.v2.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RentRequest {

    @NotNull(message = "Id must not be null")
    private Long id;
    @NotNull(message = "User id must not be null")
    private Long userId;
    @NotNull(message = "Bike id must not be null")
    private Long bikeId;
    @PositiveOrZero(message = "Days of rent must be positive or zero")
    private int daysOfRent;
}
