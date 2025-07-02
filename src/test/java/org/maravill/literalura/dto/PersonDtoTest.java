package org.maravill.literalura.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonDtoTest {
    @Test
    void testPersonDtoCreationAndGetters() {
        PersonDto dto = new PersonDto(1900, 1950, "Autor");
        assertEquals(1900, dto.birthYear());
        assertEquals(1950, dto.deathYear());
        assertEquals("Autor", dto.name());
    }

    @Test
    void testPersonDtoNulls() {
        PersonDto dto = new PersonDto(null, null, null);
        assertNull(dto.birthYear());
        assertNull(dto.deathYear());
        assertNull(dto.name());
    }
} 