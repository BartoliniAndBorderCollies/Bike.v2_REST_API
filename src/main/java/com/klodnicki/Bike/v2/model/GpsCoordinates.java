package com.klodnicki.Bike.v2.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GpsCoordinates {

    private String longitude;
    private String latitude;
}
