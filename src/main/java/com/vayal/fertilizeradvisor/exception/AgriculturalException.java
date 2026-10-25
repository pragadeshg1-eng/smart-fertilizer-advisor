package com.vayal.fertilizeradvisor.exception;

/**
 * Base domain exception for agricultural and advisory operations.
 * Demonstrates Java OOP Inheritance for domain errors.
 */
public class AgriculturalException extends RuntimeException {
    public AgriculturalException(String message) {
        super(message);
    }

    public AgriculturalException(String message, Throwable cause) {
        super(message, cause);
    }
}
