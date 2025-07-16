package com.sams.service;

import com.sams.dao.ClassSessionDAO;
import com.sams.model.ClassSession;

import java.util.List;

public class ClassSessionService {
    private ClassSessionDAO classSessionDAO = new ClassSessionDAO();

    public void saveClassSession(ClassSession classSession) {
        classSessionDAO.saveClassSession(classSession);
    }

    public void deleteClassSession(Long id) {
        classSessionDAO.deleteClassSession(id);
    }

    public List<ClassSession> getAllClassSessions() {
        return classSessionDAO.getAllClassSessions();
    }
}