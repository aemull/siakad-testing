package com.siakad.model;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ModelTests {

    @Test
    void testStudent() {
        // Test constructor with parameters
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");

        assertEquals("STU001", student.getStudentId());
        assertEquals("John Doe", student.getName());
        assertEquals("john@email.com", student.getEmail());
        assertEquals("Computer Science", student.getMajor());
        assertEquals(3, student.getSemester());
        assertEquals(3.2, student.getGpa());
        assertEquals("ACTIVE", student.getAcademicStatus());

        // Test setters
        student.setStudentId("STU002");
        student.setName("Jane Doe");
        student.setEmail("jane@email.com");
        student.setMajor("IT");
        student.setSemester(4);
        student.setGpa(3.5);
        student.setAcademicStatus("PROBATION");

        assertEquals("STU002", student.getStudentId());
        assertEquals("Jane Doe", student.getName());
        assertEquals("jane@email.com", student.getEmail());
        assertEquals("IT", student.getMajor());
        assertEquals(4, student.getSemester());
        assertEquals(3.5, student.getGpa());
        assertEquals("PROBATION", student.getAcademicStatus());

        // Test default constructor
        Student defaultStudent = new Student();
        assertNull(defaultStudent.getStudentId());
        assertNull(defaultStudent.getName());
        assertNull(defaultStudent.getEmail());
        assertNull(defaultStudent.getMajor());
        assertEquals(0, defaultStudent.getSemester());
        assertEquals(0.0, defaultStudent.getGpa());
        assertNull(defaultStudent.getAcademicStatus());
    }

    @Test
    void testCourse() {
        // Test constructor with parameters
        Course course = new Course("CS101", "Programming Basics", 3, 30, 25, "Dr. Smith");

        assertEquals("CS101", course.getCourseCode());
        assertEquals("Programming Basics", course.getCourseName());
        assertEquals(3, course.getCredits());
        assertEquals(30, course.getCapacity());
        assertEquals(25, course.getEnrolledCount());
        assertEquals("Dr. Smith", course.getLecturer());
        assertNotNull(course.getPrerequisites());
        assertTrue(course.getPrerequisites().isEmpty());

        // Test setters
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

        // Test prerequisites
        course.addPrerequisite("CS100");
        course.addPrerequisite("MA101");
        assertEquals(2, course.getPrerequisites().size());
        assertTrue(course.getPrerequisites().contains("CS100"));
        assertTrue(course.getPrerequisites().contains("MA101"));

        // Test set prerequisites
        course.setPrerequisites(Arrays.asList("PH101", "EN101"));
        assertEquals(2, course.getPrerequisites().size());
        assertTrue(course.getPrerequisites().contains("PH101"));

        // Test default constructor
        Course defaultCourse = new Course();
        assertNull(defaultCourse.getCourseCode());
        assertNull(defaultCourse.getCourseName());
        assertEquals(0, defaultCourse.getCredits());
        assertEquals(0, defaultCourse.getCapacity());
        assertEquals(0, defaultCourse.getEnrolledCount());
        assertNull(defaultCourse.getLecturer());
        assertNotNull(defaultCourse.getPrerequisites());
    }

    @Test
    void testEnrollment() {
        LocalDateTime now = LocalDateTime.now();

        // Test constructor with parameters
        Enrollment enrollment = new Enrollment("ENR001", "STU001", "CS101", now, "APPROVED");

        assertEquals("ENR001", enrollment.getEnrollmentId());
        assertEquals("STU001", enrollment.getStudentId());
        assertEquals("CS101", enrollment.getCourseCode());
        assertEquals(now, enrollment.getEnrollmentDate());
        assertEquals("APPROVED", enrollment.getStatus());

        // Test setters
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

        // Test default constructor
        Enrollment defaultEnrollment = new Enrollment();
        assertNull(defaultEnrollment.getEnrollmentId());
        assertNull(defaultEnrollment.getStudentId());
        assertNull(defaultEnrollment.getCourseCode());
        assertNull(defaultEnrollment.getEnrollmentDate());
        assertNull(defaultEnrollment.getStatus());
    }

    @Test
    void testCourseGrade() {
        // Test constructor with parameters
        CourseGrade grade = new CourseGrade("CS101", 3, 3.5);

        assertEquals("CS101", grade.getCourseCode());
        assertEquals(3, grade.getCredits());
        assertEquals(3.5, grade.getGradePoint());

        // Test setters
        grade.setCourseCode("CS102");
        grade.setCredits(4);
        grade.setGradePoint(4.0);

        assertEquals("CS102", grade.getCourseCode());
        assertEquals(4, grade.getCredits());
        assertEquals(4.0, grade.getGradePoint());

        // Test default constructor
        CourseGrade defaultGrade = new CourseGrade();
        assertNull(defaultGrade.getCourseCode());
        assertEquals(0, defaultGrade.getCredits());
        assertEquals(0.0, defaultGrade.getGradePoint());
    }
}