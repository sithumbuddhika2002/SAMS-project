
CREATE DATABASE sams_db;
USE sams_db;

CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role ENUM('ADMIN', 'LECTURER') NOT NULL
);

CREATE TABLE courses (
    id INT AUTO_INCREMENT PRIMARY KEY,
    course_name VARCHAR(100) NOT NULL,
    description TEXT
);

CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(20) UNIQUE NOT NULL,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(50),
    course_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(id)
);

CREATE TABLE lecturers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    contact VARCHAR(50)
);

CREATE TABLE subjects (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_name VARCHAR(100) NOT NULL,
    course_id INT,
    lecturer_id INT,
    FOREIGN KEY (course_id) REFERENCES courses(id),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(id)
);

CREATE TABLE classes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    subject_id INT,
    class_date DATE NOT NULL,
    start_time TIME NOT NULL,
    end_time TIME NOT NULL,
    FOREIGN KEY (subject_id) REFERENCES subjects(id)
);

CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    class_id INT,
    student_id INT,
    status ENUM('PRESENT', 'ABSENT') NOT NULL,
    attendance_date DATE NOT NULL,
    FOREIGN KEY (class_id) REFERENCES classes(id),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

-- Sample Data
INSERT INTO users (username, password, role) VALUES
('admin', 'admin123', 'ADMIN'),
('lecturer1', 'lecturer123', 'LECTURER');

INSERT INTO courses (course_name, description) VALUES
('Computer Science', 'BSc in Computer Science'),
('Mathematics', 'BSc in Mathematics');

INSERT INTO students (registration_number, name, contact, course_id) VALUES
('CS001', 'John Doe', 'john@example.com', 1),
('CS002', 'Jane Smith', 'jane@example.com', 1);

INSERT INTO lecturers (name, contact) VALUES
('Dr. Alice Brown', 'alice@example.com'),
('Prof. Bob Wilson', 'bob@example.com');

INSERT INTO subjects (subject_name, course_id, lecturer_id) VALUES
('Programming', 1, 1),
('Calculus', 2, 2);

INSERT INTO classes (subject_id, class_date, start_time, end_time) VALUES
(1, '2025-07-15', '09:00:00', '11:00:00'),
(2, '2025-07-15', '11:00:00', '13:00:00');

INSERT INTO attendance (class_id, student_id, status, attendance_date) VALUES
(1, 1, 'PRESENT', '2025-07-15'),
(1, 2, 'ABSENT', '2025-07-15');