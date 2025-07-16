
package com.sams.controller;

import com.sams.model.Attendance;
import com.sams.model.Course;
import com.sams.model.Student;
import com.sams.service.AttendanceService;
import com.sams.service.CourseService;
import com.sams.service.StudentService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.List;

public class AttendanceReportController {
    @FXML private ComboBox<Student> studentComboBox;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private DatePicker startDatePicker;
    @FXML private DatePicker endDatePicker;
    @FXML private TableView<Attendance> reportTable;
    @FXML private TableColumn<Attendance, Long> studentIdColumn;
    @FXML private TableColumn<Attendance, String> studentNameColumn;
    @FXML private TableColumn<Attendance, String> classSessionColumn;
    @FXML private TableColumn<Attendance, LocalDate> dateColumn;
    @FXML private TableColumn<Attendance, Attendance.Status> statusColumn;
    @FXML private VBox rootPane;

    private AttendanceService attendanceService = new AttendanceService();
    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        studentIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getStudent().getId()).asObject());
        studentNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudent().getName()));
        classSessionColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getClassSession().getSubject()));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        studentComboBox.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
        courseComboBox.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
    }

    @FXML
    private void generateReport() {
        Student selectedStudent = studentComboBox.getSelectionModel().getSelectedItem();
        Course selectedCourse = courseComboBox.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDatePicker.getValue();
        LocalDate endDate = endDatePicker.getValue();

        if (startDate == null || endDate == null) {
            showAlert(Alert.AlertType.ERROR, "Please select start and end dates!");
            return;
        }

        List<Attendance> report = attendanceService.getAttendanceReport(selectedStudent, selectedCourse, startDate, endDate);
        reportTable.setItems(FXCollections.observableArrayList(report));
        showAlert(Alert.AlertType.INFORMATION, "Report generated successfully!");
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
