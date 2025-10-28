package com.siakad.stub;

import com.siakad.model.Course;
import com.siakad.repository.CourseRepository;

public class StubCourseRepository implements CourseRepository {

    @Override
    public Course findByCourseCode(String courseCode) {
        switch (courseCode) {
            case "CS101":
                return new Course("CS101", "Programming Basics", 3, 30, 25, "Dr. Smith");
            case "CS201":
                Course advancedCourse = new Course("CS201", "Advanced Programming", 3, 25, 25, "Dr. Brown");
                advancedCourse.addPrerequisite("CS101");
                return advancedCourse;
            case "CS301":
                return new Course("CS301", "Data Structures", 4, 20, 20, "Dr. Wilson");
            case "FULL001":
                return new Course("FULL001", "Full Course", 3, 10, 10, "Dr. Full");
            default:
                return null;
        }
    }

    @Override
    public void update(Course course) {
        // Stub implementation - do nothing
        System.out.println("Course updated: " + course.getCourseCode());
    }

    @Override
    public boolean isPrerequisiteMet(String studentId, String courseCode) {
        if ("CS201".equals(courseCode)) {
            return "STU001".equals(studentId); // Only STU001 has met prerequisites
        }
        return true; // Other courses have no prerequisites or are met
    }
}