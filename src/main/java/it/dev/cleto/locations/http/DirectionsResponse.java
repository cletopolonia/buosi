package it.dev.cleto.locations.http;

import lombok.Data;

import java.util.List;

@Data
public class DirectionsResponse {
    private Route route;

    @Data
    public class Route {
        private List<Integer> waypoints_order;
    }
}