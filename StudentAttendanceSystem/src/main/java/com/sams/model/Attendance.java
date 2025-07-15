package com.sams.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassSession classSession;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false)
    private LocalDate attendanceDate;

    public enum Status {
        PRESENT, ABSENT
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public ClassSession getClassSession() { return classSession; }
    public void setClassSession(ClassSession classSession) { this.classSession = classSession; }
    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }
}