package com.shisha.appia.importer;

import com.shisha.appia.model.Tobacco;
import com.shisha.appia.repository.TobaccoRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TobaccoCatalogInitializerTest {

    @Mock
    private TobaccoCatalogScraper scraper;

    @Mock
    private TobaccoRepository repository;

    @InjectMocks
    private TobaccoCatalogInitializer initializer;

    @Test
    @DisplayName("Should not import tobaccos when repository already has data")
    void shouldSkipWhenRepositoryNotEmpty() throws Exception {
        when(repository.count()).thenReturn(5L);

        initializer.run(null);

        verify(scraper, never()).fetchCatalog();
        verify(repository, never()).save(any(Tobacco.class));
    }

    @Test
    @DisplayName("Should import tobaccos when repository is empty")
    void shouldImportWhenRepositoryEmpty() throws Exception {
        when(repository.count()).thenReturn(0L);
        when(scraper.fetchCatalog()).thenReturn(List.of(
                new ScrapedTobacco("Uno", "Sabor uno"),
                new ScrapedTobacco("Dos", "Sabor dos")
        ));
        when(repository.existsByName(anyString())).thenReturn(false);
        when(repository.save(any(Tobacco.class))).thenAnswer(invocation -> invocation.getArgument(0));

        initializer.run(null);

        verify(scraper, times(1)).fetchCatalog();
        verify(repository, times(2)).save(any(Tobacco.class));
    }
}
