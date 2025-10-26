package com.shisha.appia.importer;

import com.shisha.appia.model.Tobacco;
import com.shisha.appia.repository.TobaccoRepository;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Initializes the local database with tobaccos scraped from the external catalogue.
 */
@Component
@Profile("!test")
public class TobaccoCatalogInitializer implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(TobaccoCatalogInitializer.class);

    private final TobaccoCatalogScraper tobaccoCatalogScraper;
    private final TobaccoRepository tobaccoRepository;

    public TobaccoCatalogInitializer(TobaccoCatalogScraper tobaccoCatalogScraper,
                                     TobaccoRepository tobaccoRepository) {
        this.tobaccoCatalogScraper = tobaccoCatalogScraper;
        this.tobaccoRepository = tobaccoRepository;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        if (tobaccoRepository.count() > 0) {
            LOGGER.info("La base de datos de tabacos ya contiene registros. Se omite la importación inicial.");
            return;
        }

        try {
            List<ScrapedTobacco> tobaccos = tobaccoCatalogScraper.fetchCatalog();
            if (tobaccos.isEmpty()) {
                LOGGER.warn("La carta de sabores no devolvió resultados. No se han importado tabacos iniciales.");
                return;
            }

            long imported = tobaccos.stream()
                    .filter(scraped -> !tobaccoRepository.existsByName(scraped.name()))
                    .map(scraped -> tobaccoRepository.save(new Tobacco(scraped.name(), scraped.flavor())))
                    .count();

            LOGGER.info("Importación inicial de tabacos completada. Nuevos registros: {}", imported);
        } catch (TobaccoCatalogException ex) {
            LOGGER.error("No fue posible inicializar la carta de tabacos", ex);
        }
    }
}
