package com.klodnicki.Bike.v2.model;

import jakarta.persistence.Embeddable;
import lombok.*;

/**
 * Represents the GPS coordinates of a location.
 * This class is marked as Embeddable, meaning its fields can be directly included in the table of an entity class ({@link RentableVehicle}).
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class GpsCoordinates {

    /**
     * The longitude of the location.
     * Longitude is a geographic coordinate that specifies the east-west position of a point on the Earth's surface.
     */
    private String longitude;
    /**
     * The latitude of the location.
     * Latitude is a geographic coordinate that specifies the north-south position of a point on the Earth's surface.
     */
    private String latitude;
}
