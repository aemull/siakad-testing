package com.siakad.service;

import com.siakad.model.CourseGrade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;

class GradeCalculatorTest {

    private GradeCalculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new GradeCalculator();
    }

    @Test
    void testCalculateGPA_EmptyList() {
        double result = calculator.calculateGPA(new ArrayList<>());
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateGPA_NullList() {
        double result = calculator.calculateGPA(null);
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateGPA_SingleCourse() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 3.0)
        );
        double result = calculator.calculateGPA(grades);
        assertEquals(3.0, result);
    }

    @Test
    void testCalculateGPA_MultipleCourses() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 4.0),
                new CourseGrade("MA101", 4, 3.0),
                new CourseGrade("PH101", 2, 2.0)
        );
        double result = calculator.calculateGPA(grades);
        assertEquals(3.11, result, 0.01);
    }

    @Test
    void testCalculateGPA_ZeroCredits() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 0, 4.0)
        );
        double result = calculator.calculateGPA(grades);
        assertEquals(0.0, result);
    }

    @Test
    void testCalculateGPA_InvalidGradeHigh() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, 5.0)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateGPA(grades);
        });
    }

    @Test
    void testCalculateGPA_InvalidGradeNegative() {
        List<CourseGrade> grades = Arrays.asList(
                new CourseGrade("CS101", 3, -1.0)
        );
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateGPA(grades);
        });
    }

    @Test
    void testDetermineAcademicStatus_Semester1_Active() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(2.0, 1));
        assertEquals("ACTIVE", calculator.determineAcademicStatus(3.0, 1));
    }

    @Test
    void testDetermineAcademicStatus_Semester1_Probation() {
        assertEquals("PROBATION", calculator.determineAcademicStatus(1.9, 1));
        assertEquals("PROBATION", calculator.determineAcademicStatus(0.5, 1));
    }

    @Test
    void testDetermineAcademicStatus_Semester3_Active() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(2.25, 3));
        assertEquals("ACTIVE", calculator.determineAcademicStatus(3.0, 3));
    }

    @Test
    void testDetermineAcademicStatus_Semester3_Probation() {
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.0, 3));
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.24, 3));
    }

    @Test
    void testDetermineAcademicStatus_Semester3_Suspended() {
        assertEquals("SUSPENDED", calculator.determineAcademicStatus(1.9, 3));
        assertEquals("SUSPENDED", calculator.determineAcademicStatus(0.0, 3));
    }

    @Test
    void testDetermineAcademicStatus_Semester5_Active() {
        assertEquals("ACTIVE", calculator.determineAcademicStatus(2.5, 5));
        assertEquals("ACTIVE", calculator.determineAcademicStatus(4.0, 5));
    }

    @Test
    void testDetermineAcademicStatus_Semester5_Probation() {
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.0, 5));
        assertEquals("PROBATION", calculator.determineAcademicStatus(2.49, 5));
    }

    @Test
    void testDetermineAcademicStatus_Semester5_Suspended() {
        assertEquals("SUSPENDED", calculator.determineAcademicStatus(1.9, 5));
    }

    @Test
    void testDetermineAcademicStatus_InvalidGPA() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.determineAcademicStatus(5.0, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.determineAcademicStatus(-1.0, 3);
        });
    }

    @Test
    void testDetermineAcademicStatus_InvalidSemester() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.determineAcademicStatus(3.0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.determineAcademicStatus(3.0, -1);
        });
    }

    @Test
    void testCalculateMaxCredits_HighGPA() {
        assertEquals(24, calculator.calculateMaxCredits(3.0));
        assertEquals(24, calculator.calculateMaxCredits(4.0));
    }

    @Test
    void testCalculateMaxCredits_MediumGPA() {
        assertEquals(21, calculator.calculateMaxCredits(2.5));
        assertEquals(21, calculator.calculateMaxCredits(2.99));
    }

    @Test
    void testCalculateMaxCredits_LowGPA() {
        assertEquals(18, calculator.calculateMaxCredits(2.0));
        assertEquals(18, calculator.calculateMaxCredits(2.49));
    }

    @Test
    void testCalculateMaxCredits_VeryLowGPA() {
        assertEquals(15, calculator.calculateMaxCredits(1.9));
        assertEquals(15, calculator.calculateMaxCredits(0.0));
    }

    @Test
    void testCalculateMaxCredits_InvalidGPA() {
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateMaxCredits(5.0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            calculator.calculateMaxCredits(-1.0);
        });
    }
}