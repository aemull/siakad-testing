package com.siakad.stub;

import com.siakad.model.Student;
import com.siakad.repository.StudentRepository;

public class StubStudentRepository implements StudentRepository {

    @Override
    public Student findById(String studentId) {
        if ("STU001".equals(studentId)) {
            return new Student("STU001", "John", "john@email.com", "CS", 3, 3.2, "ACTIVE");
        }
        return null;
    }

    @Override
    public void update(Student student) {
        // Do nothing - stub method
    }

    @Override
    public java.util.List<com.siakad.model.Course> getCompletedCourses(String studentId) {
        return java.util.Arrays.asList(); // Return empty list for simplicity
    }
}