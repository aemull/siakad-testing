package com.siakad.service;

import com.siakad.stub.StubStudentRepository;
import com.siakad.stub.StubCourseRepository;
import com.siakad.stub.StubNotificationService;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class SiakadStubTest {

    @Test
    void testCreditLimitWithStub() {
        // Setup
        StubStudentRepository studentRepo = new StubStudentRepository();
        StubCourseRepository courseRepo = new StubCourseRepository();
        StubNotificationService notificationService = new StubNotificationService();
        GradeCalculator gradeCalculator = new GradeCalculator();

        EnrollmentService service = new EnrollmentService(
                studentRepo, courseRepo, notificationService, gradeCalculator
        );

        // Test - Student with GPA 3.2 can take up to 24 credits
        assertTrue(service.validateCreditLimit("STU001", 20));
        assertFalse(service.validateCreditLimit("STU001", 25));
    }
}