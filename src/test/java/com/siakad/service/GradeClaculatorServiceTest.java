package com.siakad.service;

import com.siakad.model.CourseGrade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    private GradeCalculator gradeCalculator;

    @BeforeEach
    void setUp() {
        gradeCalculator = new GradeCalculator();
    }

    @Test
    @DisplayName("Test calculateGPA dengan list kosong")
    void testCalculateGPA_EmptyList() {
        List<CourseGrade> emptyList = new ArrayList<>();
        double result = gradeCalculator.calculateGPA(emptyList);
        assertEquals(0.0, result);
    }

    @Test
    @DisplayName("Test calculateGPA dengan satu mata kuliah")
    void testCalculateGPA_SingleCourse() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 3.0)
        );
        double result = gradeCalculator.calculateGPA(grades);
        assertEquals(3.0, result);
    }

    @Test
    @DisplayName("Test calculateGPA dengan multiple courses")
    void testCalculateGPA_MultipleCourses() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 4.0),
                new CourseGrade("MA101", 4, 3.0),
                new CourseGrade("PH101", 2, 2.0)
        );
        double result = gradeCalculator.calculateGPA(grades);
        assertEquals(3.11, result, 0.01);
    }

    @Test
    @DisplayName("Test calculateGPA dengan grade point invalid")
    void testCalculateGPA_InvalidGradePoint() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 5.0)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            gradeCalculator.calculateGPA(grades);
        });
    }

    @Test
    @DisplayName("Test calculateGPA dengan grade point negatif")
    void testCalculateGPA_NegativeGradePoint() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, -1.0)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            gradeCalculator.calculateGPA(grades);
        });
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 1-2 ACTIVE")
    void testDetermineAcademicStatus_Semester1_Active() {
        String status = gradeCalculator.determineAcademicStatus(2.5, 1);
        assertEquals("ACTIVE", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 1-2 PROBATION")
    void testDetermineAcademicStatus_Semester2_Probation() {
        String status = gradeCalculator.determineAcademicStatus(1.8, 2);
        assertEquals("PROBATION", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 3-4 ACTIVE")
    void testDetermineAcademicStatus_Semester3_Active() {
        String status = gradeCalculator.determineAcademicStatus(2.5, 3);
        assertEquals("ACTIVE", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 3-4 PROBATION")
    void testDetermineAcademicStatus_Semester4_Probation() {
        String status = gradeCalculator.determineAcademicStatus(2.1, 4);
        assertEquals("PROBATION", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 3-4 SUSPENDED")
    void testDetermineAcademicStatus_Semester4_Suspended() {
        String status = gradeCalculator.determineAcademicStatus(1.9, 4);
        assertEquals("SUSPENDED", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 5+ ACTIVE")
    void testDetermineAcademicStatus_Semester6_Active() {
        String status = gradeCalculator.determineAcademicStatus(2.7, 6);
        assertEquals("ACTIVE", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 5+ PROBATION")
    void testDetermineAcademicStatus_Semester6_Probation() {
        String status = gradeCalculator.determineAcademicStatus(2.3, 6);
        assertEquals("PROBATION", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus semester 5+ SUSPENDED")
    void testDetermineAcademicStatus_Semester6_Suspended() {
        String status = gradeCalculator.determineAcademicStatus(1.8, 6);
        assertEquals("SUSPENDED", status);
    }

    @Test
    @DisplayName("Test determineAcademicStatus dengan GPA invalid")
    void testDetermineAcademicStatus_InvalidGPA() {
        assertThrows(IllegalArgumentException.class, () -> {
            gradeCalculator.determineAcademicStatus(5.0, 3);
        });
    }

    @Test
    @DisplayName("Test determineAcademicStatus dengan semester invalid")
    void testDetermineAcademicStatus_InvalidSemester() {
        assertThrows(IllegalArgumentException.class, () -> {
            gradeCalculator.determineAcademicStatus(3.0, 0);
        });
    }

    @Test
    @DisplayName("Test calculateMaxCredits dengan GPA tinggi")
    void testCalculateMaxCredits_HighGPA() {
        int maxCredits = gradeCalculator.calculateMaxCredits(3.5);
        assertEquals(24, maxCredits);
    }

    @Test
    @DisplayName("Test calculateMaxCredits dengan GPA medium")
    void testCalculateMaxCredits_MediumGPA() {
        int maxCredits = gradeCalculator.calculateMaxCredits(2.7);
        assertEquals(21, maxCredits);
    }

    @Test
    @DisplayName("Test calculateMaxCredits dengan GPA rendah")
    void testCalculateMaxCredits_LowGPA() {
        int maxCredits = gradeCalculator.calculateMaxCredits(1.5);
        assertEquals(15, maxCredits);
    }

    @Test
    @DisplayName("Test calculateMaxCredits dengan GPA batas")
    void testCalculateMaxCredits_BorderGPA() {
        assertEquals(24, gradeCalculator.calculateMaxCredits(3.0));
        assertEquals(21, gradeCalculator.calculateMaxCredits(2.5));
        assertEquals(18, gradeCalculator.calculateMaxCredits(2.0));
        assertEquals(15, gradeCalculator.calculateMaxCredits(1.9));
    }

    @Test
    @DisplayName("Test calculateMaxCredits dengan GPA invalid")
    void testCalculateMaxCredits_InvalidGPA() {
        assertThrows(IllegalArgumentException.class, () -> {
            gradeCalculator.calculateMaxCredits(5.0);
        });
    }
}