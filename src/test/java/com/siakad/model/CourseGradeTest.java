package com.siakad.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class CourseGradeTest {

    private CourseGrade courseGrade;

    @BeforeEach
    void setUp() {
        courseGrade = new CourseGrade("CS101", 3, 3.5);
    }

    @Test
    @DisplayName("Test constructor dan getters")
    void testConstructorAndGetters() {
        assertEquals("CS101", courseGrade.getCourseCode());
        assertEquals(3, courseGrade.getCredits());
        assertEquals(3.5, courseGrade.getGradePoint());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        courseGrade.setCourseCode("CS102");
        courseGrade.setCredits(4);
        courseGrade.setGradePoint(4.0);

        assertEquals("CS102", courseGrade.getCourseCode());
        assertEquals(4, courseGrade.getCredits());
        assertEquals(4.0, courseGrade.getGradePoint());
    }

    @Test
    @DisplayName("Test default constructor")
    void testDefaultConstructor() {
        CourseGrade defaultCourseGrade = new CourseGrade();
        assertNull(defaultCourseGrade.getCourseCode());
        assertEquals(0, defaultCourseGrade.getCredits());
        assertEquals(0.0, defaultCourseGrade.getGradePoint());
    }
}