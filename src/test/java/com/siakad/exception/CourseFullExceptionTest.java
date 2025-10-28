package com.siakad.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseFullExceptionTest {

    @Test
    @DisplayName("Test CourseFullException dengan message")
    void testCourseFullExceptionWithMessage() {
        String message = "Course is full";
        CourseFullException exception = new CourseFullException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test CourseFullException dengan message dan cause")
    void testCourseFullExceptionWithMessageAndCause() {
        String message = "Course is full";
        Throwable cause = new RuntimeException("Root cause");
        CourseFullException exception = new CourseFullException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}