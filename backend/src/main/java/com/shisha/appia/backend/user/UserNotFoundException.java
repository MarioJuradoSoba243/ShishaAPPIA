package com.shisha.appia.backend.user;

/**
 * Exception thrown when a user cannot be located in persistence storage.
 */
public class UserNotFoundException extends RuntimeException {

    /**
     * Creates the exception with a missing identifier.
     *
     * @param id identifier that was not found
     */
    public UserNotFoundException(Long id) {
        super("User not found with id=" + id);
    }
}
