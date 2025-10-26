package com.shisha.appia.importer;

/**
 * Exception thrown when the tobacco catalogue cannot be retrieved or parsed.
 */
public class TobaccoCatalogException extends RuntimeException {

    /**
     * Creates a new exception with the provided message and cause.
     *
     * @param message description of the failure
     * @param cause   original cause
     */
    public TobaccoCatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}
