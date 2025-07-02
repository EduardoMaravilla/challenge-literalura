package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BookshelfTest {
    @Test
    void testBookshelfConstructorAndGetters() {
        Bookshelf shelf = new Bookshelf(1L, "Estante");
        assertEquals(1L, shelf.getId());
        assertEquals("Estante", shelf.getName());
    }

    @Test
    void testBookshelfSettersAndNulls() {
        Bookshelf shelf = new Bookshelf();
        shelf.setId(2L);
        shelf.setName(null);
        assertEquals(2L, shelf.getId());
        assertNull(shelf.getName());
    }
} 