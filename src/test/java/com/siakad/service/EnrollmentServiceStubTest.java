package com.siakad.service;

import com.siakad.stub.StubStudentRepository;
import com.siakad.stub.StubCourseRepository;
import com.siakad.stub.StubNotificationService;
import com.siakad.exception.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

class EnrollmentServiceStubTest {

    private EnrollmentService enrollmentService;
    private StubStudentRepository studentRepository;
    private StubCourseRepository courseRepository;
    private StubNotificationService notificationService;
    private GradeCalculator gradeCalculator;

    @BeforeEach
    void setUp() {
        studentRepository = new StubStudentRepository();
        courseRepository = new StubCourseRepository();
        notificationService = new StubNotificationService();
        gradeCalculator = new GradeCalculator();

        enrollmentService = new EnrollmentService(
                studentRepository, courseRepository,
                notificationService, gradeCalculator
        );
    }

    @Test
    @DisplayName("Test validateCreditLimit dengan student aktif")
    void testValidateCreditLimit_ActiveStudent() {
        // Student dengan GPA 3.2 seharusnya bisa mengambil maksimal 24 SKS
        boolean result1 = enrollmentService.validateCreditLimit("STU001", 20);
        boolean result2 = enrollmentService.validateCreditLimit("STU001", 24);
        boolean result3 = enrollmentService.validateCreditLimit("STU001", 25);

        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
    }

    @Test
    @DisplayName("Test validateCreditLimit dengan student probation")
    void testValidateCreditLimit_ProbationStudent() {
        // Student dengan GPA 1.8 seharusnya hanya bisa mengambil maksimal 15 SKS
        boolean result1 = enrollmentService.validateCreditLimit("STU002", 15);
        boolean result2 = enrollmentService.validateCreditLimit("STU002", 16);

        assertTrue(result1);
        assertFalse(result2);
    }

    @Test
    @DisplayName("Test validateCreditLimit dengan student tidak ditemukan")
    void testValidateCreditLimit_StudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.validateCreditLimit("STU999", 20);
        });
    }

    @Test
    @DisplayName("Test dropCourse berhasil")
    void testDropCourse_Success() {
        // Should not throw any exception
        assertDoesNotThrow(() -> {
            enrollmentService.dropCourse("STU001", "CS101");
        });

        // Verify notification was sent
        assertEquals(1, notificationService.getSentEmails().size());
        assertTrue(notificationService.getSentEmails().get(0).contains("Course Drop Confirmation"));
    }

    @Test
    @DisplayName("Test dropCourse dengan student tidak ditemukan")
    void testDropCourse_StudentNotFound() {
        assertThrows(StudentNotFoundException.class, () -> {
            enrollmentService.dropCourse("STU999", "CS101");
        });
    }

    @Test
    @DisplayName("Test dropCourse dengan course tidak ditemukan")
    void testDropCourse_CourseNotFound() {
        assertThrows(CourseNotFoundException.class, () -> {
            enrollmentService.dropCourse("STU001", "UNKNOWN");
        });
    }
}