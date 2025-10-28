package com.siakad.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class StudentTest {

    private Student student;

    @BeforeEach
    void setUp() {
        student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
    }

    @Test
    @DisplayName("Test constructor dan getters")
    void testConstructorAndGetters() {
        assertEquals("STU001", student.getStudentId());
        assertEquals("John Doe", student.getName());
        assertEquals("john@email.com", student.getEmail());
        assertEquals("Computer Science", student.getMajor());
        assertEquals(3, student.getSemester());
        assertEquals(3.2, student.getGpa());
        assertEquals("ACTIVE", student.getAcademicStatus());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        student.setStudentId("STU002");
        student.setName("Jane Doe");
        student.setEmail("jane@email.com");
        student.setMajor("Information Technology");
        student.setSemester(4);
        student.setGpa(3.5);
        student.setAcademicStatus("PROBATION");

        assertEquals("STU002", student.getStudentId());
        assertEquals("Jane Doe", student.getName());
        assertEquals("jane@email.com", student.getEmail());
        assertEquals("Information Technology", student.getMajor());
        assertEquals(4, student.getSemester());
        assertEquals(3.5, student.getGpa());
        assertEquals("PROBATION", student.getAcademicStatus());
    }

    @Test
    @DisplayName("Test default constructor")
    void testDefaultConstructor() {
        Student defaultStudent = new Student();
        assertNull(defaultStudent.getStudentId());
        assertNull(defaultStudent.getName());
        assertNull(defaultStudent.getEmail());
        assertNull(defaultStudent.getMajor());
        assertEquals(0, defaultStudent.getSemester());
        assertEquals(0.0, defaultStudent.getGpa());
        assertNull(defaultStudent.getAcademicStatus());
    }
}