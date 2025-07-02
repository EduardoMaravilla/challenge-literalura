package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    void testBookConstructorAndGetters() {
        Book book = new Book(1L, "Titulo", List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", 10);
        assertEquals(1L, book.getIdGutenberg());
        assertEquals("Titulo", book.getTitle());
        assertTrue(book.getAuthors().isEmpty());
        assertTrue(book.getTranslators().isEmpty());
        assertTrue(book.getSubjects().isEmpty());
        assertTrue(book.getBookshelves().isEmpty());
        assertTrue(book.getLanguages().isEmpty());
        assertTrue(book.getCopyright());
        assertEquals("media", book.getMediaType());
        assertEquals(10, book.getDownloadCount());
    }

    @Test
    void testBookSettersAndNulls() {
        Book book = new Book();
        book.setId(2L);
        book.setIdGutenberg(3L);
        book.setTitle("Otro");
        book.setAuthors(null);
        book.setTranslators(null);
        book.setSubjects(null);
        book.setBookshelves(null);
        book.setLanguages(null);
        book.setCopyright(false);
        book.setMediaType(null);
        book.setDownloadCount(null);
        assertEquals(2L, book.getId());
        assertEquals(3L, book.getIdGutenberg());
        assertEquals("Otro", book.getTitle());
        assertNull(book.getAuthors());
        assertNull(book.getTranslators());
        assertNull(book.getSubjects());
        assertNull(book.getBookshelves());
        assertNull(book.getLanguages());
        assertFalse(book.getCopyright());
        assertNull(book.getMediaType());
        assertNull(book.getDownloadCount());
    }
} 