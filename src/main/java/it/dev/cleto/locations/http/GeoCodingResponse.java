package it.dev.cleto.locations.http;

import it.dev.cleto.locations.exceptions.ExceptionMissmatchedCity;
import it.dev.cleto.locations.model.GeoResult;
import it.dev.cleto.locations.model.buosi.BuosiOrder;
import lombok.Data;
import lombok.extern.log4j.Log4j;

import java.util.List;

@Log4j
@Data
public class GeoCodingResponse {
    private List<GeoResult> results;

    public void check(BuosiOrder buosiOrder) throws ExceptionMissmatchedCity {
        if (this.getResults().size() > 1) {
            log.warn("invokeApiGeoCoding - address: " + this.results.get(0).getAddress());
            log.warn("invokeApiGeoCoding - results: " + this.getResults().size());
        }
        final GeoResult geoResult = this.getResults().get(0);
        if (!geoResult.getLocation_type().equals("exact")) {
            if (!buosiOrder.getCity().equalsIgnoreCase(geoResult.getLocality()) && !geoResult.getAddress().toLowerCase().contains(buosiOrder.getCity().toLowerCase())) {
                log.error("Mismatched cities: " + geoResult.getLocality());
                throw new ExceptionMissmatchedCity(buosiOrder.getCity(), geoResult.getLocality());
            }
            log.warn("invokeApiGeoCoding - address: " + geoResult.getAddress());
            log.warn("invokeApiGeoCoding - getLocationType: " + geoResult.getLocation_type());
            log.warn("invokeApiGeoCoding - type: " + geoResult.getType());
        }
    }

}
