package org.maravill.literalura.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookDtoTest {
    @Test
    void testBookDtoCreationAndGetters() {
        BookDto dto = new BookDto(1L, "Titulo", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of("text/plain", "url"), 10);
        assertEquals(1L, dto.id());
        assertEquals("Titulo", dto.title());
        assertTrue(dto.authors().isEmpty());
        assertTrue(dto.sumaries().isEmpty());
        assertTrue(dto.translators().isEmpty());
        assertTrue(dto.subjects().isEmpty());
        assertTrue(dto.bookshelves().isEmpty());
        assertTrue(dto.languages().isEmpty());
        assertTrue(dto.copyright());
        assertEquals("media", dto.mediaType());
        assertEquals("url", dto.formats().get("text/plain"));
        assertEquals(10, dto.downloadCount());
    }

    @Test
    void testBookDtoNulls() {
        BookDto dto = new BookDto(null, null, null, null, null, null, null, null, null, null, null, null);
        assertNull(dto.id());
        assertNull(dto.title());
        assertNull(dto.authors());
        assertNull(dto.sumaries());
        assertNull(dto.translators());
        assertNull(dto.subjects());
        assertNull(dto.bookshelves());
        assertNull(dto.languages());
        assertNull(dto.copyright());
        assertNull(dto.mediaType());
        assertNull(dto.formats());
        assertNull(dto.downloadCount());
    }
} 