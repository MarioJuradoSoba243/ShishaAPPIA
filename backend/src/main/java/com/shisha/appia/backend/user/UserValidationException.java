package com.shisha.appia.backend.user;

/**
 * Exception raised when user data violates business constraints.
 */
public class UserValidationException extends RuntimeException {

    /**
     * Builds an exception with a descriptive message.
     *
     * @param message validation failure description
     */
    public UserValidationException(String message) {
        super(message);
    }
}
