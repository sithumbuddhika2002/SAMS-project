
package com.sams.controller;

import com.sams.model.User;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class DashboardController {
    @FXML private Label welcomeLabel;
    @FXML private Button courseButton;
    @FXML private Button studentButton;
    @FXML private Button lecturerButton;
    @FXML private Button classButton;
    @FXML private Button attendanceButton;
    @FXML private Button reportButton;
    @FXML private VBox rootPane;

    private User user;

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();
    }

    public void setUser(User user) {
        this.user = user;
        welcomeLabel.setText("Welcome, " + user.getUsername() + " (" + user.getRole() + ")");
        if (user.getRole() == User.Role.LECTURER) {
            courseButton.setDisable(true);
            studentButton.setDisable(true);
            lecturerButton.setDisable(true);
        }
    }

    @FXML private void navigateToCourseManagement() throws IOException { loadScreen("/fxml/CourseManagement.fxml", "Course Management"); }
    @FXML private void navigateToStudentManagement() throws IOException { loadScreen("/fxml/StudentManagement.fxml", "Student Management"); }
    @FXML private void navigateToLecturerManagement() throws IOException { loadScreen("/fxml/LecturerManagement.fxml", "Lecturer Management"); }
    @FXML private void navigateToClassManagement() throws IOException { loadScreen("/fxml/ClassManagement.fxml", "Class Management"); }
    @FXML private void navigateToAttendanceManagement() throws IOException { loadScreen("/fxml/AttendanceManagement.fxml", "Attendance Management"); }
    @FXML private void navigateToAttendanceReport() throws IOException { loadScreen("/fxml/AttendanceReport.fxml", "Attendance Report"); }

    private void loadScreen(String fxmlPath, String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) welcomeLabel.getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.setTitle(title);
        stage.show();
    }
}
