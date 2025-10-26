package com.shisha.appia.backend.user;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that manages {@link User} persistence.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Finds a user by email.
     *
     * @param email email to search for
     * @return an optional containing the user if found
     */
    Optional<User> findByEmail(String email);
}
