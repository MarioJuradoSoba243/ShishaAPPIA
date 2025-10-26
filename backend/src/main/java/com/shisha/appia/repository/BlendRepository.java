package com.shisha.appia.repository;

import com.shisha.appia.model.Blend;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository for persisting {@link Blend} entities.
 */
public interface BlendRepository extends JpaRepository<Blend, Long> {
}
