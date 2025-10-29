package com.siakad.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTest {

    private String message;
    private Throwable cause;

    @BeforeEach
    void setUp(){
        message = "Message";
        cause = new IllegalStateException("Cause Message");
    }

    @Test
    void testCourseFullExceptionMessageAndCause(){
        CourseFullException courseFullException = new CourseFullException(message, cause);

        assertEquals(message, courseFullException.getMessage());
        assertEquals(cause, courseFullException.getCause());
    }

    @Test
    void testCourseNotFoundExceptionMessageAndCause(){
        CourseNotFoundException courseNotFoundException = new CourseNotFoundException(message, cause);

        assertEquals(message, courseNotFoundException.getMessage());
        assertEquals(cause, courseNotFoundException.getCause());
    }

    @Test
    void testEnrollmentExceptionMessageAndCause(){
        EnrollmentException enrollmentException = new EnrollmentException(message, cause);

        assertEquals(message, enrollmentException.getMessage());
        assertEquals(cause, enrollmentException.getCause());
    }

    @Test
    void testPrerequisiteNotMetExceptionMessageAndCause(){
        PrerequisiteNotMetException prerequisiteNotMetException = new PrerequisiteNotMetException(message, cause);

        assertEquals(message, prerequisiteNotMetException.getMessage());
        assertEquals(cause, prerequisiteNotMetException.getCause());
    }

    @Test
    void testStudentNotFoundExceptionMessageAndCause(){
        StudentNotFoundException studentNotFoundException = new StudentNotFoundException(message, cause);

        assertEquals(message, studentNotFoundException.getMessage());
        assertEquals(cause, studentNotFoundException.getCause());
    }

}