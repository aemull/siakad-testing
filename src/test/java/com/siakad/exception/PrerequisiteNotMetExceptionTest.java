package com.siakad.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PrerequisiteNotMetExceptionTest {

    @Test
    @DisplayName("Test PrerequisiteNotMetException dengan message")
    void testPrerequisiteNotMetExceptionWithMessage() {
        String message = "Prerequisites not met";
        PrerequisiteNotMetException exception = new PrerequisiteNotMetException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test PrerequisiteNotMetException dengan message dan cause")
    void testPrerequisiteNotMetExceptionWithMessageAndCause() {
        String message = "Prerequisites not met";
        Throwable cause = new RuntimeException("Root cause");
        PrerequisiteNotMetException exception = new PrerequisiteNotMetException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}