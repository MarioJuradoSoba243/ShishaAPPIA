package com.shisha.appia.importer;

/**
 * Value object holding the data scraped from the external tobacco catalogue.
 *
 * @param name   tobacco name as published by the provider
 * @param flavor flavor description extracted from the catalogue
 */
public record ScrapedTobacco(String name, String flavor) {
}
