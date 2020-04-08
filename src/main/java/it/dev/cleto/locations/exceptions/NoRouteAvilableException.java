package it.dev.cleto.locations.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NoRouteAvilableException extends Exception {
    public NoRouteAvilableException(String responseAsString) {
        super(responseAsString);
    }
}
