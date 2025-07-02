package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.models.SearchTitleHistory;
import org.maravill.literalura.repositories.ISearchTitleHistoryRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchTitleHistoryServiceImplTest {
    private ISearchTitleHistoryRepository searchTitleHistoryRepository;
    private SearchTitleHistoryServiceImpl searchTitleHistoryService;

    @BeforeEach
    void setUp() {
        searchTitleHistoryRepository = mock(ISearchTitleHistoryRepository.class);
        searchTitleHistoryService = new SearchTitleHistoryServiceImpl(searchTitleHistoryRepository);
    }

    @Test
    void testIsTitleInHistory_true() {
        when(searchTitleHistoryRepository.existsByTitle("titulo")).thenReturn(true);
        assertTrue(searchTitleHistoryService.isTitleInHistory("titulo"));
    }

    @Test
    void testIsTitleInHistory_false() {
        when(searchTitleHistoryRepository.existsByTitle("titulo")).thenReturn(false);
        assertFalse(searchTitleHistoryService.isTitleInHistory("titulo"));
    }

    @Test
    void testSaveSearchTitleHistory() {
        searchTitleHistoryService.saveSearchTitleHistory("titulo");
        verify(searchTitleHistoryRepository).save(any(SearchTitleHistory.class));
    }
} 