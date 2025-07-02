package org.maravill.literalura.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SummaryTest {
    @Test
    void testSummaryConstructorAndGetters() {
        Summary summary = new Summary(1L, "Resumen");
        assertEquals(1L, summary.getId());
        assertEquals("Resumen", summary.getName());
    }

    @Test
    void testSummarySettersAndNulls() {
        Summary summary = new Summary();
        summary.setId(2L);
        summary.setName(null);
        assertEquals(2L, summary.getId());
        assertNull(summary.getName());
    }
} 