
package com.sams.dao;

import com.sams.model.ClassSession;
import com.sams.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

public class ClassSessionDAO {
    public void saveClassSession(ClassSession classSession) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(classSession);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public void deleteClassSession(Long id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            ClassSession classSession = session.get(ClassSession.class, id);
            if (classSession != null) {
                session.delete(classSession);
                transaction.commit();
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }

    public List<ClassSession> getAllClassSessions() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<ClassSession> query = session.createQuery("FROM ClassSession", ClassSession.class);
            return query.list();
        }
    }
}
