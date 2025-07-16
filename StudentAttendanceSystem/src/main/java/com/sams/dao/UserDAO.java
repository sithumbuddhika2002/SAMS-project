package com.sams.dao;

import com.sams.model.User;
import com.sams.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

public class UserDAO {
    public User authenticate(String username, String password) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User WHERE username = :username AND password = :password", User.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.uniqueResult();
        }
    }
}