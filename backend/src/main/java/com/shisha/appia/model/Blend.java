package com.shisha.appia.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a blend composed of multiple tobacco flavours.
 */
@Entity
@Table(name = "blends")
public class Blend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @OrderBy("name ASC")
    @JoinTable(
            name = "blend_tobaccos",
            joinColumns = @JoinColumn(name = "blend_id"),
            inverseJoinColumns = @JoinColumn(name = "tobacco_id")
    )
    private Set<Tobacco> tobaccos = new LinkedHashSet<>();

    public Blend() {
        // Default constructor required by JPA
    }

    public Blend(String name, String description) {
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Tobacco> getTobaccos() {
        return tobaccos;
    }

    public void setTobaccos(Set<Tobacco> tobaccos) {
        this.tobaccos = tobaccos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Blend blend = (Blend) o;
        return Objects.equals(id, blend.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
