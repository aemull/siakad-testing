package com.siakad.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentTest {

    private Enrollment enrollment;
    private LocalDateTime testDate;

    @BeforeEach
    void setUp() {
        testDate = LocalDateTime.now();
        enrollment = new Enrollment("ENR001", "STU001", "CS101", testDate, "APPROVED");
    }

    @Test
    @DisplayName("Test constructor dan getters")
    void testConstructorAndGetters() {
        assertEquals("ENR001", enrollment.getEnrollmentId());
        assertEquals("STU001", enrollment.getStudentId());
        assertEquals("CS101", enrollment.getCourseCode());
        assertEquals(testDate, enrollment.getEnrollmentDate());
        assertEquals("APPROVED", enrollment.getStatus());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        LocalDateTime newDate = LocalDateTime.now().plusDays(1);

        enrollment.setEnrollmentId("ENR002");
        enrollment.setStudentId("STU002");
        enrollment.setCourseCode("CS102");
        enrollment.setEnrollmentDate(newDate);
        enrollment.setStatus("REJECTED");

        assertEquals("ENR002", enrollment.getEnrollmentId());
        assertEquals("STU002", enrollment.getStudentId());
        assertEquals("CS102", enrollment.getCourseCode());
        assertEquals(newDate, enrollment.getEnrollmentDate());
        assertEquals("REJECTED", enrollment.getStatus());
    }

    @Test
    @DisplayName("Test default constructor")
    void testDefaultConstructor() {
        Enrollment defaultEnrollment = new Enrollment();
        assertNull(defaultEnrollment.getEnrollmentId());
        assertNull(defaultEnrollment.getStudentId());
        assertNull(defaultEnrollment.getCourseCode());
        assertNull(defaultEnrollment.getEnrollmentDate());
        assertNull(defaultEnrollment.getStatus());
    }
}