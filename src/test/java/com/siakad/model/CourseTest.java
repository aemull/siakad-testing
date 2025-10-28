package com.siakad.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course("CS101", "Programming Basics", 3, 30, 25, "Dr. Smith");
    }

    @Test
    @DisplayName("Test constructor dan getters")
    void testConstructorAndGetters() {
        assertEquals("CS101", course.getCourseCode());
        assertEquals("Programming Basics", course.getCourseName());
        assertEquals(3, course.getCredits());
        assertEquals(30, course.getCapacity());
        assertEquals(25, course.getEnrolledCount());
        assertEquals("Dr. Smith", course.getLecturer());
        assertNotNull(course.getPrerequisites());
        assertTrue(course.getPrerequisites().isEmpty());
    }

    @Test
    @DisplayName("Test setters")
    void testSetters() {
        course.setCourseCode("CS102");
        course.setCourseName("Advanced Programming");
        course.setCredits(4);
        course.setCapacity(40);
        course.setEnrolledCount(35);
        course.setLecturer("Dr. Johnson");

        assertEquals("CS102", course.getCourseCode());
        assertEquals("Advanced Programming", course.getCourseName());
        assertEquals(4, course.getCredits());
        assertEquals(40, course.getCapacity());
        assertEquals(35, course.getEnrolledCount());
        assertEquals("Dr. Johnson", course.getLecturer());
    }

    @Test
    @DisplayName("Test addPrerequisite")
    void testAddPrerequisite() {
        course.addPrerequisite("CS100");
        course.addPrerequisite("MA101");

        List<String> prerequisites = course.getPrerequisites();
        assertEquals(2, prerequisites.size());
        assertTrue(prerequisites.contains("CS100"));
        assertTrue(prerequisites.contains("MA101"));
    }

    @Test
    @DisplayName("Test setPrerequisites")
    void testSetPrerequisites() {
        List<String> prereqs = Arrays.asList("CS100", "MA101");
        course.setPrerequisites(prereqs);

        assertEquals(2, course.getPrerequisites().size());
        assertTrue(course.getPrerequisites().containsAll(prereqs));
    }

    @Test
    @DisplayName("Test default constructor")
    void testDefaultConstructor() {
        Course defaultCourse = new Course();
        assertNull(defaultCourse.getCourseCode());
        assertNull(defaultCourse.getCourseName());
        assertEquals(0, defaultCourse.getCredits());
        assertEquals(0, defaultCourse.getCapacity());
        assertEquals(0, defaultCourse.getEnrolledCount());
        assertNull(defaultCourse.getLecturer());
        assertNotNull(defaultCourse.getPrerequisites());
    }
}