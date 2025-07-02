package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.models.Language;
import org.maravill.literalura.repositories.ILanguageRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LanguageServiceImplTest {
    private ILanguageRepository languageRepository;
    private LanguageServiceImpl languageService;

    @BeforeEach
    void setUp() {
        languageRepository = mock(ILanguageRepository.class);
        languageService = new LanguageServiceImpl(languageRepository);
    }

    @Test
    void testFindOrCreateLanguages_emptyList() {
        assertEquals(Collections.emptyList(), languageService.findOrCreateLanguages(Collections.emptyList()));
    }

    @Test
    void testFindOrCreateLanguages_success() {
        Language lang = new Language("Español");
        when(languageRepository.findByName("Español")).thenReturn(Optional.empty());
        when(languageRepository.save(lang)).thenReturn(lang);
        assertEquals(Collections.singletonList(lang), languageService.findOrCreateLanguages(Collections.singletonList(lang)));
    }

    @Test
    void testFindOrCreateLanguages_existing() {
        Language lang = new Language("Inglés");
        when(languageRepository.findByName("Inglés")).thenReturn(Optional.of(lang));
        assertEquals(Collections.singletonList(lang), languageService.findOrCreateLanguages(Collections.singletonList(lang)));
        verify(languageRepository, never()).save(any());
    }

    @Test
    void testFindOrCreateLanguages_invalidName() {
        Language lang = new Language("");
        assertThrows(IllegalArgumentException.class, () -> languageService.findOrCreateLanguages(Collections.singletonList(lang)));
    }
} 