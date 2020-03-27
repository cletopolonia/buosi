package it.dev.cleto.locations.exceptions;

public class NoGeoCodingRouteAvilableException extends Exception {
    public NoGeoCodingRouteAvilableException(String address) {
        super(address);
    }
}
