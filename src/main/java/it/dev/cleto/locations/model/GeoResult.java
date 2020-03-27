package it.dev.cleto.locations.model;

import lombok.Data;

@Data
public class GeoResult {
    private String address; //Via dei Tigli, 21040 Venegono inferiore VA, Italia
    private String region; //Lombardia
    private String area; //Provincia di Varese
    private String locality; //Pianbosco
    private String street; //Via dei tigli
    private String house; //1
    private GeoLocation location;
    private String location_type; //exact o approximate o centroid
    private String type; //street_address
}
