package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {
    @Test
    void testLanguageConstructorAndGetters() {
        Language lang = new Language(1L, "Español");
        assertEquals(1L, lang.getId());
        assertEquals("Español", lang.getName());
    }

    @Test
    void testLanguageSettersAndNulls() {
        Language lang = new Language();
        lang.setId(2L);
        lang.setName(null);
        assertEquals(2L, lang.getId());
        assertNull(lang.getName());
    }
} 