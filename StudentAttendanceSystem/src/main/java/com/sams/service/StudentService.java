package com.sams.service;

import com.sams.dao.StudentDAO;
import com.sams.model.Student;

import java.util.List;

public class StudentService {
    private StudentDAO studentDAO = new StudentDAO();

    public void saveStudent(Student student) {
        studentDAO.saveStudent(student);
    }

    public void deleteStudent(Long id) {
        studentDAO.deleteStudent(id);
    }

    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }
}