package it.dev.cleto.locations.model;

import lombok.Data;

@Data
public class GeoLocation {
    private Double lat;
    private Double lng;

    public String getGeoLocation() {
        return lat + "," + lng;
    }

}