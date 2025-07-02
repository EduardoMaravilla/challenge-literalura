package org.maravill.literalura.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.maravill.literalura.models.Bookshelf;
import org.maravill.literalura.repositories.IBookshelfRepository;
import java.util.Collections;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookshelfServiceImplTest {
    private IBookshelfRepository bookshelfRepository;
    private BookshelfServiceImpl bookshelfService;

    @BeforeEach
    void setUp() {
        bookshelfRepository = mock(IBookshelfRepository.class);
        bookshelfService = new BookshelfServiceImpl(bookshelfRepository);
    }

    @Test
    void testFindOrCreateBookshelves_emptyList() {
        assertEquals(Collections.emptyList(), bookshelfService.findOrCreateBookshelves(Collections.emptyList()));
    }

    @Test
    void testFindOrCreateBookshelves_success() {
        Bookshelf shelf = new Bookshelf("Ficción");
        when(bookshelfRepository.findByName("Ficción")).thenReturn(Optional.empty());
        when(bookshelfRepository.save(shelf)).thenReturn(shelf);
        assertEquals(Collections.singletonList(shelf), bookshelfService.findOrCreateBookshelves(Collections.singletonList(shelf)));
    }

    @Test
    void testFindOrCreateBookshelves_existing() {
        Bookshelf shelf = new Bookshelf("Historia");
        when(bookshelfRepository.findByName("Historia")).thenReturn(Optional.of(shelf));
        assertEquals(Collections.singletonList(shelf), bookshelfService.findOrCreateBookshelves(Collections.singletonList(shelf)));
        verify(bookshelfRepository, never()).save(any());
    }

    @Test
    void testFindOrCreateBookshelves_invalidName() {
        Bookshelf shelf = new Bookshelf("");
        assertThrows(IllegalArgumentException.class, () -> bookshelfService.findOrCreateBookshelves(Collections.singletonList(shelf)));
    }
} 