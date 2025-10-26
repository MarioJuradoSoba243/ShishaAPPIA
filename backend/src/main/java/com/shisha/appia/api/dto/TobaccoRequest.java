package com.shisha.appia.api.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * Request payload used to create a new {@code Tobacco}.
 */
public class TobaccoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El sabor es obligatorio")
    private String flavor;

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
}
