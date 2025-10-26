package com.shisha.appia.importer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Retrieves and parses the public tobacco catalogue provided by En La Nube Shop.
 */
@Component
public class TobaccoCatalogScraper {

    static final String CATALOG_URL = "https://www.enlanubeshop.com/carta-de-sabores/";
    private static final int CONNECTION_TIMEOUT_MILLIS = 15_000;
    private static final Logger LOGGER = LoggerFactory.getLogger(TobaccoCatalogScraper.class);

    /**
     * Downloads the external catalogue and extracts the list of tobaccos.
     *
     * @return list of scraped tobaccos containing name and flavor
     */
    public List<ScrapedTobacco> fetchCatalog() {
        try {
            Document document = Jsoup.connect(CATALOG_URL)
                    .userAgent("Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36")
                    .timeout(CONNECTION_TIMEOUT_MILLIS)
                    .maxBodySize(0)
                    .get();
            return parseDocument(document);
        } catch (IOException ex) {
            throw new TobaccoCatalogException("No se pudo obtener la carta de sabores", ex);
        }
    }

    List<ScrapedTobacco> parseDocument(Document document) {
        Map<String, ScrapedTobacco> scraped = new LinkedHashMap<>();
        Elements cards = document.select("div:has(> h4):has(p > strong:matchesOwn((?i)Aroma))");
        for (Element card : cards) {
            Element title = card.selectFirst("h4");
            Element aromaParagraph = card.selectFirst("p:has(> strong:matchesOwn((?i)Aroma))");
            if (title == null || aromaParagraph == null) {
                continue;
            }
            String name = cleanText(title.text());
            String flavor = extractFlavor(aromaParagraph);
            if (name.isEmpty() || flavor.isEmpty()) {
                continue;
            }
            scraped.putIfAbsent(name, new ScrapedTobacco(name, flavor));
        }
        LOGGER.debug("Scraped {} tobaccos from catalogue", scraped.size());
        return new ArrayList<>(scraped.values());
    }

    private String extractFlavor(Element aromaParagraph) {
        Element clone = aromaParagraph.clone();
        clone.select("strong").remove();
        String flavorText = cleanText(clone.text());
        if (flavorText.isEmpty()) {
            flavorText = cleanText(aromaParagraph.ownText());
        }
        if (flavorText.toLowerCase().startsWith("aroma")) {
            flavorText = flavorText.replaceFirst("(?i)^aroma\\s*:?", "").trim();
        }
        return flavorText;
    }

    private String cleanText(String value) {
        if (value == null) {
            return "";
        }
        String normalized = value.replace('\u00a0', ' ').trim();
        return normalized.replaceAll("\\s+", " ");
    }
}
