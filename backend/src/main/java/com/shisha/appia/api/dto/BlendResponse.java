package com.shisha.appia.api.dto;

import java.util.List;

/**
 * Representation of a blend returned by the API.
 *
 * @param id          database identifier
 * @param name        blend name
 * @param description blend description
 * @param tobaccos    tobaccos included in the blend
 */
public record BlendResponse(Long id, String name, String description, List<TobaccoResponse> tobaccos) {
}
