package com.shisha.appia.api.dto;

/**
 * Representation of a tobacco returned by the API.
 *
 * @param id    database identifier
 * @param name  tobacco name
 * @param brand tobacco brand
 */
public record TobaccoResponse(Long id, String name, String brand) {
}
