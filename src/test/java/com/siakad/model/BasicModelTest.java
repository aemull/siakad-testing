package com.siakad.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class BasicModelTest {

    @Test
    void testAllModels() {
        // Test Student
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        assertEquals("STU001", student.getStudentId());

        // Test Course
        Course course = new Course("CS101", "Programming", 3, 30, 25, "Dr. Smith");
        assertEquals("CS101", course.getCourseCode());
        course.addPrerequisite("CS100");
        assertTrue(course.getPrerequisites().contains("CS100"));

        // Test Enrollment
        LocalDateTime now = LocalDateTime.now();
        Enrollment enrollment = new Enrollment("ENR001", "STU001", "CS101", now, "APPROVED");
        assertEquals("APPROVED", enrollment.getStatus());

        // Test CourseGrade
        CourseGrade grade = new CourseGrade("CS101", 3, 3.5);
        assertEquals(3.5, grade.getGradePoint());
    }
}