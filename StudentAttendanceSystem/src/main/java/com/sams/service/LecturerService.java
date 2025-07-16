package com.sams.service;

import com.sams.dao.LecturerDAO;
import com.sams.model.Lecturer;

import java.util.List;

public class LecturerService {
    private LecturerDAO lecturerDAO = new LecturerDAO();

    public void saveLecturer(Lecturer lecturer) {
        lecturerDAO.saveLecturer(lecturer);
    }

    public void deleteLecturer(Long id) {
        lecturerDAO.deleteLecturer(id);
    }

    public List<Lecturer> getAllLecturers() {
        return lecturerDAO.getAllLecturers();
    }
}