package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FormatTest {
    @Test
    void testFormatConstructorAndGetters() {
        Format format = new Format(1L, "text/plain", "url");
        assertEquals(1L, format.getId());
        assertEquals("text/plain", format.getMimeType());
        assertEquals("url", format.getUrl());
    }

    @Test
    void testFormatSettersAndNulls() {
        Format format = new Format();
        format.setId(2L);
        format.setMimeType(null);
        format.setUrl(null);
        assertEquals(2L, format.getId());
        assertNull(format.getMimeType());
        assertNull(format.getUrl());
    }
} 