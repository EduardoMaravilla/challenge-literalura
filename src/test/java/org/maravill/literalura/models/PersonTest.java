package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {
    @Test
    void testPersonConstructorAndGetters() {
        Person person = new Person(1L, 1900, 1950, "Autor");
        assertEquals(1L, person.getId());
        assertEquals(1900, person.getBirthYear());
        assertEquals(1950, person.getDeathYear());
        assertEquals("Autor", person.getName());
    }

    @Test
    void testPersonSettersAndNulls() {
        Person person = new Person();
        person.setId(2L);
        person.setBirthYear(null);
        person.setDeathYear(null);
        person.setName(null);
        assertEquals(2L, person.getId());
        assertNull(person.getBirthYear());
        assertNull(person.getDeathYear());
        assertNull(person.getName());
    }
} 