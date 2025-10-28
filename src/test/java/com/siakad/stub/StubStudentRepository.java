package com.siakad.stub;

import com.siakad.model.Course;
import com.siakad.model.Student;
import com.siakad.repository.StudentRepository;
import java.util.Arrays;
import java.util.List;

public class StubStudentRepository implements StudentRepository {

    @Override
    public Student findById(String studentId) {
        switch (studentId) {
            case "STU001":
                return new Student("STU001", "John Doe", "john@email.com",
                        "Computer Science", 3, 3.2, "ACTIVE");
            case "STU002":
                return new Student("STU002", "Jane Smith", "jane@email.com",
                        "Information Technology", 2, 1.8, "PROBATION");
            case "STU003":
                return new Student("STU003", "Bob Johnson", "bob@email.com",
                        "Computer Science", 5, 2.3, "SUSPENDED");
            default:
                return null;
        }
    }

    @Override
    public void update(Student student) {
        // Stub implementation - do nothing
        System.out.println("Student updated: " + student.getStudentId());
    }

    @Override
    public List<Course> getCompletedCourses(String studentId) {
        if ("STU001".equals(studentId)) {
            return Arrays.asList(
                    new Course("CS101", "Programming Basics", 3, 30, 25, "Dr. Smith"),
                    new Course("MA101", "Calculus", 4, 40, 35, "Dr. Johnson")
            );
        }
        return Arrays.asList();
    }
}