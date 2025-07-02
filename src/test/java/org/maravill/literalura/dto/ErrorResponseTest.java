package org.maravill.literalura.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ErrorResponseTest {
    @Test
    void testErrorResponseCreationAndGetters() {
        ErrorResponse error = new ErrorResponse("Error message");
        assertEquals("Error message", error.details());
    }
} 