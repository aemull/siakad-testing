package com.siakad.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StudentNotFoundExceptionTest {

    @Test
    @DisplayName("Test StudentNotFoundException dengan message")
    void testStudentNotFoundExceptionWithMessage() {
        String message = "Student not found";
        StudentNotFoundException exception = new StudentNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test StudentNotFoundException dengan message dan cause")
    void testStudentNotFoundExceptionWithMessageAndCause() {
        String message = "Student not found";
        Throwable cause = new RuntimeException("Root cause");
        StudentNotFoundException exception = new StudentNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}