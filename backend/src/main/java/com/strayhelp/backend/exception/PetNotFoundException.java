package com.strayhelp.backend.exception;

// Thrown when a Pet lookup by id finds nothing; translated to HTTP 404
// by the global exception handler.
public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(String message) {
        super(message);
    }
}
