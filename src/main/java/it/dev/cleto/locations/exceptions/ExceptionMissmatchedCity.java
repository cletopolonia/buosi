package it.dev.cleto.locations.exceptions;

public class ExceptionMissmatchedCity extends Exception {
    public ExceptionMissmatchedCity(String city, String locality) {
        super("Expected: " + city + " obtain: " + locality);
    }
}
