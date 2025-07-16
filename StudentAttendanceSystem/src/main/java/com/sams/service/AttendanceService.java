package com.sams.service;

import com.sams.dao.AttendanceDAO;
import com.sams.model.Attendance;
import com.sams.model.Course;
import com.sams.model.Student;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import org.hibernate.Session;
import org.hibernate.query.Query;
import com.sams.util.HibernateUtil;

public class AttendanceService {
    private AttendanceDAO attendanceDAO = new AttendanceDAO();

    public void saveAttendance(Attendance attendance) {
        attendanceDAO.saveAttendance(attendance);
    }

    public List<Attendance> getAttendanceReport(Student student, Course course, LocalDate startDate, LocalDate endDate) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            String hql = "FROM Attendance a WHERE a.attendanceDate BETWEEN :startDate AND :endDate";
            if (student != null) {
                hql += " AND a.student = :student";
            }
            if (course != null) {
                hql += " AND a.classSession.course = :course";
            }
            Query<Attendance> query = session.createQuery(hql, Attendance.class);
            query.setParameter("startDate", startDate);
            query.setParameter("endDate", endDate);
            if (student != null) {
                query.setParameter("student", student);
            }
            if (course != null) {
                query.setParameter("course", course);
            }
            return query.list();
        }
    }
}