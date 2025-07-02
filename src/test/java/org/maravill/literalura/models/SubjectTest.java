package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SubjectTest {
    @Test
    void testSubjectConstructorAndGetters() {
        Subject subject = new Subject(1L, "Tema");
        assertEquals(1L, subject.getId());
        assertEquals("Tema", subject.getName());
    }

    @Test
    void testSubjectSettersAndNulls() {
        Subject subject = new Subject();
        subject.setId(2L);
        subject.setName(null);
        assertEquals(2L, subject.getId());
        assertNull(subject.getName());
    }
} 