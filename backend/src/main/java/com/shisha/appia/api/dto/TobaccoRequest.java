package com.shisha.appia.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload used to create a new {@code Tobacco}.
 */
public class TobaccoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "La marca es obligatoria")
    private String brand;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
