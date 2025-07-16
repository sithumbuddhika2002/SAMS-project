
package com.sams.dao;

import com.sams.model.Lecturer;
import com.sams.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class LecturerDAO {
    public void saveLecturer(Lecturer lecturer) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(lecturer);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteLecturer(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Lecturer lecturer = session.get(Lecturer.class, id);
            if (lecturer != null) {
                session.delete(lecturer);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<Lecturer> getAllLecturers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<Lecturer> query = session.createQuery("FROM Lecturer", Lecturer.class);
            return query.list();
        }
    }
}
