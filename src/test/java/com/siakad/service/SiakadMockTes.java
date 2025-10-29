package com.siakad.service;

import com.siakad.model.Student;
import com.siakad.model.Course;
import com.siakad.repository.StudentRepository;
import com.siakad.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SiakadMockTest {

    @Mock
    private StudentRepository studentRepo;

    @Mock
    private CourseRepository courseRepo;

    @Mock
    private NotificationService notificationService;

    private EnrollmentService enrollmentService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        enrollmentService = new EnrollmentService(
                studentRepo, courseRepo, notificationService, new GradeCalculator()
        );
    }

    @Test
    void testEnrollSuccess() {
        // Setup mock behavior
        Student student = new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        Course course = new Course("CS101", "Programming", 3, 30, 20, "Dr. Smith");

        when(studentRepo.findById("STU001")).thenReturn(student);
        when(courseRepo.findByCourseCode("CS101")).thenReturn(course);
        when(courseRepo.isPrerequisiteMet("STU001", "CS101")).thenReturn(true);

        // Test
        var enrollment = enrollmentService.enrollCourse("STU001", "CS101");

        // Verify
        assertNotNull(enrollment);
        assertEquals("STU001", enrollment.getStudentId());
        verify(studentRepo).findById("STU001");
        verify(notificationService).sendEmail(anyString(), anyString(), anyString());
    }

    @Test
    void testStudentNotFound() {
        when(studentRepo.findById("UNKNOWN")).thenReturn(null);

        assertThrows(com.siakad.exception.StudentNotFoundException.class, () -> {
            enrollmentService.enrollCourse("UNKNOWN", "CS101");
        });
    }
}