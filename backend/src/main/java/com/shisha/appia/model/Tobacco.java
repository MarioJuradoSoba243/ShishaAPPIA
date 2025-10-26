package com.shisha.appia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Objects;

/**
 * Represents a shisha tobacco available in the catalogue.
 */
@Entity
@Table(name = "tobaccos")
public class Tobacco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String flavor;

    public Tobacco() {
        // Default constructor required by JPA
    }

    public Tobacco(String name, String flavor) {
        this.name = name;
        this.flavor = flavor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Tobacco tobacco = (Tobacco) o;
        return Objects.equals(id, tobacco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
