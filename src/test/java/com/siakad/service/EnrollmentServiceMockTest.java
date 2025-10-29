package com.siakad.service;

import com.siakad.exception.*;
import com.siakad.model.Student;
import com.siakad.model.Course;
import com.siakad.model.Enrollment;
import com.siakad.repository.StudentRepository;
import com.siakad.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentServiceTest {

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
                studentRepository, courseRepository, notificationService, gradeCalculator
        );
    }

    @Test
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

        // Verify
        verify(studentRepository).findById("STU001");
        verify(courseRepository).findByCourseCode("CS101");
        verify(courseRepository).isPrerequisiteMet("STU001", "CS101");
        verify(courseRepository).update(course);
        verify(notificationService).sendEmail("john@email.com",
                "Enrollment Confirmation",
                "You have been enrolled in: Programming Basics");
    }

    @Test
    void testEnrollCourse_StudentNotFound() {
        // Arrange
        when(studentRepository.findById("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.enrollCourse("UNKNOWN", "CS101");
        });

        verify(studentRepository).findById("UNKNOWN");
        verify(courseRepository, never()).findByCourseCode(anyString());
    }

    @Test
    void testEnrollCourse_StudentSuspended() {
        // Arrange
        Student suspendedStudent = new Student("STU003", "Bob", "bob@email.com",
                "CS", 5, 2.3, "SUSPENDED");
        when(studentRepository.findById("STU003")).thenReturn(suspendedStudent);

        // Act & Assert
        EnrollmentException exception = assertThrows(EnrollmentException.class, () -> {
            enrollmentService.enrollCourse("STU003", "CS101");
        });
        assertEquals("Student is suspended", exception.getMessage());

        verify(studentRepository).findById("STU003");
        verify(courseRepository, never()).findByCourseCode(anyString());
    }

    @Test
    void testEnrollCourse_CourseNotFound() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            enrollmentService.enrollCourse("STU001", "UNKNOWN");
        });

        verify(studentRepository).findById("STU001");
        verify(courseRepository).findByCourseCode("UNKNOWN");
    }

    @Test
    void testEnrollCourse_CourseFull() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        Course fullCourse = new Course("CS101", "Programming", 3, 30, 30, "Dr. Smith");

        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS101")).thenReturn(fullCourse);

        // Act & Assert
        assertThrows(CourseFullException.class, () -> {
            enrollmentService.enrollCourse("STU001", "CS101");
        });

        verify(studentRepository).findById("STU001");
        verify(courseRepository).findByCourseCode("CS101");
        verify(courseRepository, never()).isPrerequisiteMet(anyString(), anyString());
    }

    @Test
    void testEnrollCourse_PrerequisiteNotMet() {
        // Arrange
        Student student = new Student("STU002", "Jane", "jane@email.com", "IT", 2, 1.8, "PROBATION");
        Course course = new Course("CS201", "Advanced Programming", 3, 25, 20, "Dr. Brown");

        when(studentRepository.findById("STU002")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS201")).thenReturn(course);
        when(courseRepository.isPrerequisiteMet("STU002", "CS201")).thenReturn(false);

        // Act & Assert
        assertThrows(PrerequisiteNotMetException.class, () -> {
            enrollmentService.enrollCourse("STU002", "CS201");
        });

        verify(studentRepository).findById("STU002");
        verify(courseRepository).findByCourseCode("CS201");
        verify(courseRepository).isPrerequisiteMet("STU002", "CS201");
        verify(courseRepository, never()).update(any(Course.class));
        verify(notificationService, never()).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testValidateCreditLimit_WithinLimit() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);

        // Act
        boolean result = enrollmentService.validateCreditLimit("STU001", 20);

        // Assert
        assertTrue(result);
        verify(studentRepository).findById("STU001");
    }

    @Test
    void testValidateCreditLimit_ExceedLimit() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);

        // Act
        boolean result = enrollmentService.validateCreditLimit("STU001", 25);

        // Assert
        assertFalse(result);
        verify(studentRepository).findById("STU001");
    }

    @Test
    void testValidateCreditLimit_StudentNotFound() {
        // Arrange
        when(studentRepository.findById("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.validateCreditLimit("UNKNOWN", 20);
        });

        verify(studentRepository).findById("UNKNOWN");
    }

    @Test
    void testDropCourse_Success() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        Course course = new Course("CS101", "Programming", 3, 30, 25, "Dr. Smith");

        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("CS101")).thenReturn(course);

        // Act
        enrollmentService.dropCourse("STU001", "CS101");

        // Assert - enrolled count should decrease by 1
        assertEquals(24, course.getEnrolledCount());

        // Verify
        verify(studentRepository).findById("STU001");
        verify(courseRepository).findByCourseCode("CS101");
        verify(courseRepository).update(course);
        verify(notificationService).sendEmail("john@email.com",
                "Course Drop Confirmation",
                "You have dropped: Programming");
    }

    @Test
    void testDropCourse_StudentNotFound() {
        // Arrange
        when(studentRepository.findById("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.dropCourse("UNKNOWN", "CS101");
        });

        verify(studentRepository).findById("UNKNOWN");
        verify(courseRepository, never()).findByCourseCode(anyString());
    }

    @Test
    void testDropCourse_CourseNotFound() {
        // Arrange
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        when(studentRepository.findById("STU001")).thenReturn(student);
        when(courseRepository.findByCourseCode("UNKNOWN")).thenReturn(null);

        // Act & Assert
        assertThrows(CourseNotFoundException.class, () -> {
            enrollmentService.dropCourse("STU001", "UNKNOWN");
        });

        verify(studentRepository).findById("STU001");
        verify(courseRepository).findByCourseCode("UNKNOWN");
    }
}