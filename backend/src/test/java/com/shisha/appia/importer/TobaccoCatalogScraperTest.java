package com.shisha.appia.importer;

import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TobaccoCatalogScraperTest {

    private final TobaccoCatalogScraper scraper = new TobaccoCatalogScraper();

    @Test
    @DisplayName("Should extract names and flavors from catalogue document")
    void shouldParseTobaccoCards() {
        String html = """
                <div>
                    <div class='py-4 px-2'>
                        <h4>MR.SHISHA CAMDOWN - 50g</h4>
                        <div class='info'>
                            <p><strong>Aroma: </strong>BATIDO DE MELON CANTALUPO.</p>
                        </div>
                    </div>
                    <div class='py-4 px-2'>
                        <h4>MR.SHISHA COOLIO - 50g y 200g</h4>
                        <div class='info'>
                            <p><strong>Aroma: </strong>BATIDO DE PLATANO.</p>
                        </div>
                    </div>
                </div>
                """;

        Document document = Jsoup.parse(html);
        List<ScrapedTobacco> tobaccos = scraper.parseDocument(document);

        assertThat(tobaccos).hasSize(2);
        assertThat(tobaccos.get(0).name()).isEqualTo("MR.SHISHA CAMDOWN - 50g");
        assertThat(tobaccos.get(0).flavor()).isEqualTo("BATIDO DE MELON CANTALUPO.");
        assertThat(tobaccos.get(1).name()).isEqualTo("MR.SHISHA COOLIO - 50g y 200g");
        assertThat(tobaccos.get(1).flavor()).isEqualTo("BATIDO DE PLATANO.");
    }

    @Test
    @DisplayName("Should skip duplicated tobacco names")
    void shouldSkipDuplicates() {
        String html = """
                <div>
                    <div class='py-4 px-2'>
                        <h4>Duplicated</h4>
                        <div class='info'>
                            <p><strong>Aroma:</strong> Uno</p>
                        </div>
                    </div>
                    <div class='py-4 px-2'>
                        <h4>Duplicated</h4>
                        <div class='info'>
                            <p><strong>Aroma:</strong> Dos</p>
                        </div>
                    </div>
                </div>
                """;

        Document document = Jsoup.parse(html);
        List<ScrapedTobacco> tobaccos = scraper.parseDocument(document);

        assertThat(tobaccos).singleElement().satisfies(scraped -> {
            assertThat(scraped.name()).isEqualTo("Duplicated");
            assertThat(scraped.flavor()).isEqualTo("Uno");
        });
    }
}
