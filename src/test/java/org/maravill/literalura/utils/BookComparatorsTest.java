package org.maravill.literalura.utils;

import org.junit.jupiter.api.Test;
import org.maravill.literalura.dto.BookDto;
import org.maravill.literalura.dto.PersonDto;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class BookComparatorsTest {
    @Test
    void testTitleComparator() {
        BookDto b1 = new BookDto(1L, "A", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 1);
        BookDto b2 = new BookDto(2L, "B", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 2);
        assertTrue(BookComparators.TITLE_ASC.compare(b1, b2) < 0);
    }

    @Test
    void testAuthorComparator() {
        BookDto b1 = new BookDto(1L, "A", List.of(new PersonDto(1900, 1950, "Autor1")), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 1);
        BookDto b2 = new BookDto(2L, "B", List.of(new PersonDto(1900, 1950, "Autor2")), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 2);
        assertTrue(BookComparators.AUTHOR_ASC.compare(b1, b2) < 0);
    }

    @Test
    void testDownloadCountComparator() {
        BookDto b1 = new BookDto(1L, "A", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 5);
        BookDto b2 = new BookDto(2L, "B", List.of(), List.of(), List.of(), List.of(), List.of(), List.of(), true, "media", Map.of(), 10);
        assertTrue(BookComparators.DOWNLOAD_ASC.compare(b1, b2) < 0);
    }
} 