package org.maravill.literalura.dto;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class GutendexResponseTest {
    @Test
    void testGutendexResponseCreationAndGetters() {
        GutendexResponse response = new GutendexResponse(1L, "next", "prev", List.of());
        assertEquals(1L, response.count());
        assertEquals("next", response.next());
        assertEquals("prev", response.previous());
        assertTrue(response.results().isEmpty());
    }

    @Test
    void testGutendexResponseNulls() {
        GutendexResponse response = new GutendexResponse(null, null, null, null);
        assertNull(response.count());
        assertNull(response.next());
        assertNull(response.previous());
        assertNull(response.results());
    }
} 