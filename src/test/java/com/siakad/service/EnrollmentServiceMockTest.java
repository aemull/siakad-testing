package com.siakad.service;

import com.siakad.exception.*;
import com.siakad.model.Course;
import com.siakad.model.Student;
import com.siakad.model.Enrollment;
import com.siakad.repository.CourseRepository;
import com.siakad.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentServiceMockTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private NotificationService notificationService;

    private GradeCalculator gradeCalculator;
    private EnrollmentService enrollmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gradeCalculator = new GradeCalculator();
        enrollmentService = new EnrollmentService(
                studentRepository, courseRepository,
                notificationService, gradeCalculator
        );
    }

    @Test
    @DisplayName("Test enrollCourse berhasil")
    void testEnrollCourse_Success() {
        // Arrange
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
        Course course = new Course("CS101", "Programming Basics", 3, 30, 20, "Dr. Smith");

        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS101")).thenReturn(course);
        when(courseRepository.isPrerequisiteMet("STU001", "CS101")).thenReturn(true);

        // Act
        Enrollment enrollment = enrollmentService.enrollCourse("STU001", "CS101");

        // Assert
        assertNotNull(enrollment);
        assertEquals("STU001", enrollment.getStudentId());
        assertEquals("CS101", enrollment.getCourseCode());
        assertEquals("APPROVED", enrollment.getStatus());
        assertNotNull(enrollment.getEnrollmentDate());

        // Verify interactions
        verify(studentRepository, times(1)).findById("STU001");
        verify(courseRepository, times(1)).findByCourseCode("CS101");
        verify(courseRepository, times(1)).isPrerequisiteMet("STU001", "CS101");
        verify(courseRepository, times(1)).update(course);
        verify(notificationService, times(1)).sendEmail(
                eq("john@email.com"),
                eq("Enrollment Confirmation"),
                contains("Programming Basics")
        );
    }

    @Test
    @DisplayName("Test enrollCourse - student tidak ditemukan")
    void testEnrollCourse_StudentNotFound() {
        // Arrange
        when(studentRepository.findById("STU999")).thenReturn(null);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.enrollCourse("STU999", "CS101");
        });

        verify(studentRepository, times(1)).findById("STU999");
        verify(courseRepository, never()).findByCourseCode(anyString());
    }

    @Test
    @DisplayName("Test enrollCourse - student suspended")
    void testEnrollCourse_StudentSuspended() {
        // Arrange
        Student suspendedStudent = new Student("STU003", "Bob Johnson", "bob@email.com",
                "Computer Science", 5, 2.3, "SUSPENDED");
        when(studentRepository.findById("STU003")).thenReturn(suspendedStudent);

        // Act & Assert
        assertThrows(EnrollmentException.class, () -> {
            enrollmentService.enrollCourse("STU003", "CS101");
        });

        verify(studentRepository, times(1)).findById("STU003");
        verify(courseRepository, never()).findByCourseCode(anyString());
    }

    @Test
    @DisplayName("Test enrollCourse - course tidak ditemukan")
    void testEnrollCourse_CourseNotFound() {
        // Arrange
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            enrollmentService.enrollCourse("STU001", "UNKNOWN");
        });

        verify(studentRepository, times(1)).findById("STU001");
        verify(courseRepository, times(1)).findByCourseCode("UNKNOWN");
        verify(courseRepository, never()).isPrerequisiteMet(anyString(), anyString());
    }

    @Test
    @DisplayName("Test enrollCourse - course penuh")
    void testEnrollCourse_CourseFull() {
        // Arrange
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
        Course fullCourse = new Course("FULL001", "Full Course", 3, 10, 10, "Dr. Full");

        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("FULL001")).thenReturn(fullCourse);

        // Act & Assert
        assertThrows(CourseFullException.class, () -> {
            enrollmentService.enrollCourse("STU001", "FULL001");
        });

        verify(studentRepository, times(1)).findById("STU001");
        verify(courseRepository, times(1)).findByCourseCode("FULL001");
        verify(courseRepository, never()).isPrerequisiteMet(anyString(), anyString());
    }

    @Test
    @DisplayName("Test enrollCourse - prerequisite tidak terpenuhi")
    void testEnrollCourse_PrerequisiteNotMet() {
        // Arrange
        Student student = new Student("STU002", "Jane Smith", "jane@email.com",
                "Information Technology", 2, 1.8, "PROBATION");
        Course advancedCourse = new Course("CS201", "Advanced Programming", 3, 25, 20, "Dr. Brown");

        when(studentRepository.findById("STU002")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS201")).thenReturn(advancedCourse);
        when(courseRepository.isPrerequisiteMet("STU002", "CS201")).thenReturn(false);

        // Act & Assert
        assertThrows(PrerequisiteNotMetException.class, () -> {
            enrollmentService.enrollCourse("STU002", "CS201");
        });

        verify(studentRepository, times(1)).findById("STU002");
        verify(courseRepository, times(1)).findByCourseCode("CS201");
        verify(courseRepository, times(1)).isPrerequisiteMet("STU002", "CS201");
        verify(courseRepository, never()).update(any(Course.class));
        verify(notificationService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Test dropCourse berhasil")
    void testDropCourse_Success() {
        // Arrange
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
        Course course = new Course("CS101", "Programming Basics", 3, 30, 25, "Dr. Smith");

        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS101")).thenReturn(course);

        // Act
        enrollmentService.dropCourse("STU001", "CS101");

        // Assert - course enrolled count should be decreased
        assertEquals(24, course.getEnrolledCount());

        // Verify interactions
        verify(studentRepository, times(1)).findById("STU001");
        verify(courseRepository, times(1)).findByCourseCode("CS101");
        verify(courseRepository, times(1)).update(course);
        verify(notificationService, times(1)).sendEmail(
                eq("john@email.com"),
                eq("Course Drop Confirmation"),
                contains("Programming Basics")
        );
    }

    @Test
    @DisplayName("Test validateCreditLimit berhasil")
    void testValidateCreditLimit_Success() {
        // Arrange
        Student student = new Student("STU001", "John Doe", "john@email.com",
                "Computer Science", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);

        // Act
        boolean result1 = enrollmentService.validateCreditLimit("STU001", 20);
        boolean result2 = enrollmentService.validateCreditLimit("STU001", 25);

        // Assert
        assertTrue(result1); // 20 <= 24 (max credits for GPA 3.2)
        assertFalse(result2); // 25 > 24

        verify(studentRepository, times(2)).findById("STU001");
    }
}