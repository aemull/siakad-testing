package com.siakad.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentExceptionTest {

    @Test
    @DisplayName("Test EnrollmentException dengan message")
    void testEnrollmentExceptionWithMessage() {
        String message = "Enrollment error occurred";
        EnrollmentException exception = new EnrollmentException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test EnrollmentException dengan message dan cause")
    void testEnrollmentExceptionWithMessageAndCause() {
        String message = "Enrollment error occurred";
        Throwable cause = new RuntimeException("Root cause");
        EnrollmentException exception = new EnrollmentException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}