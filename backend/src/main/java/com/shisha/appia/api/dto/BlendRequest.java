package com.shisha.appia.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * Request payload for registering a new blend.
 */
public class BlendRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    private String description;

    @NotEmpty(message = "La mezcla debe incluir al menos un tabaco")
    @Size(min = 1, message = "La mezcla debe incluir al menos un tabaco")
    private List<Long> tobaccoIds;

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

    public List<Long> getTobaccoIds() {
        return tobaccoIds;
    }

    public void setTobaccoIds(List<Long> tobaccoIds) {
        this.tobaccoIds = tobaccoIds;
    }
}
