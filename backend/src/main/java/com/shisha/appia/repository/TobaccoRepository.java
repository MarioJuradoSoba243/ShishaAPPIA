package com.shisha.appia.repository;

import com.shisha.appia.model.Tobacco;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting {@link Tobacco} entities.
 */
public interface TobaccoRepository extends JpaRepository<Tobacco, Long> {
}
