package com.shisha.appia.repository;

import com.shisha.appia.model.Tobacco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting {@link Tobacco} entities.
 */
public interface TobaccoRepository extends JpaRepository<Tobacco, Long> {

    /**
     * Checks whether a tobacco with the provided name already exists.
     *
     * @param name tobacco name to check
     * @return {@code true} if an entity exists with that name, {@code false} otherwise
     */
    boolean existsByName(String name);
}
