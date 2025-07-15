package com.sams.service;

import com.sams.dao.UserDAO;
import com.sams.model.User;

public class UserService {
    private UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.authenticate(username, password);
    }
}