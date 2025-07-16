
package com.sams.controller;

import com.sams.model.Attendance;
import com.sams.model.ClassSession;
import com.sams.model.Student;
import com.sams.service.AttendanceService;
import com.sams.service.ClassSessionService;
import com.sams.service.StudentService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class AttendanceManagementController {
    @FXML private ComboBox<ClassSession> classComboBox;
    @FXML private TableView<Attendance> attendanceTable;
    @FXML private TableColumn<Attendance, Long> studentIdColumn;
    @FXML private TableColumn<Attendance, String> studentNameColumn;
    @FXML private TableColumn<Attendance, Attendance.Status> statusColumn;
    @FXML private VBox rootPane;

    private AttendanceService attendanceService = new AttendanceService();
    private ClassSessionService classSessionService = new ClassSessionService();
    private StudentService studentService = new StudentService();

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        studentIdColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleLongProperty(cellData.getValue().getStudent().getId()).asObject());
        studentNameColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getStudent().getName()));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        classComboBox.setItems(FXCollections.observableArrayList(classSessionService.getAllClassSessions()));
        classComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadAttendance(newSelection);
            }
        });
    }

    @FXML
    private void markPresent() {
        Attendance selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(Attendance.Status.PRESENT);
            attendanceTable.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student!");
        }
    }

    @FXML
    private void markAbsent() {
        Attendance selected = attendanceTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setStatus(Attendance.Status.ABSENT);
            attendanceTable.refresh();
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student!");
        }
    }

    @FXML
    private void saveAttendance() {
        ClassSession selectedClass = classComboBox.getSelectionModel().getSelectedItem();
        if (selectedClass != null) {
            for (Attendance attendance : attendanceTable.getItems()) {
                attendance.setClassSession(selectedClass);
                attendance.setAttendanceDate(LocalDate.now());
                attendanceService.saveAttendance(attendance);
            }
            showAlert(Alert.AlertType.INFORMATION, "Attendance saved successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a class session!");
        }
    }

    private void loadAttendance(ClassSession classSession) {
        attendanceTable.getItems().clear();
        for (Student student : studentService.getAllStudents()) {
            Attendance attendance = new Attendance();
            attendance.setStudent(student);
            attendance.setStatus(Attendance.Status.ABSENT);
            attendanceTable.getItems().add(attendance);
        }
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
