package com.sams.dao;

import com.sams.model.Attendance;
import com.sams.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AttendanceDAO {
    public void saveAttendance(Attendance attendance) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.saveOrUpdate(attendance);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            throw e;
        }
    }
}