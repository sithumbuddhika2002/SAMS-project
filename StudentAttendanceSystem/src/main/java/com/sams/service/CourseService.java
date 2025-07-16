package com.sams.service;

import com.sams.dao.CourseDAO;
import com.sams.model.Course;

import java.util.List;

public class CourseService {
    private CourseDAO courseDAO = new CourseDAO();

    public void saveCourse(Course course) {
        courseDAO.saveCourse(course);
    }

    public void deleteCourse(Long id) {
        courseDAO.deleteCourse(id);
    }

    public List<Course> getAllCourses() {
        return courseDAO.getAllCourses();
    }
}