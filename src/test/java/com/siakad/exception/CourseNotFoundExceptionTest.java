package com.siakad.exception;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CourseNotFoundExceptionTest {

    @Test
    @DisplayName("Test CourseNotFoundException dengan message")
    void testCourseNotFoundExceptionWithMessage() {
        String message = "Course not found";
        CourseNotFoundException exception = new CourseNotFoundException(message);

        assertEquals(message, exception.getMessage());
        assertNull(exception.getCause());
    }

    @Test
    @DisplayName("Test CourseNotFoundException dengan message dan cause")
    void testCourseNotFoundExceptionWithMessageAndCause() {
        String message = "Course not found";
        Throwable cause = new RuntimeException("Root cause");
        CourseNotFoundException exception = new CourseNotFoundException(message, cause);

        assertEquals(message, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}