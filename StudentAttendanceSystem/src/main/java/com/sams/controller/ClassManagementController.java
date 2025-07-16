
package com.sams.controller;

import com.sams.model.ClassSession;
import com.sams.model.Course;
import com.sams.service.ClassSessionService;
import com.sams.service.CourseService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ClassManagementController {
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private TextField subjectField;
    @FXML private TextField startTimeField;
    @FXML private TextField endTimeField;
    @FXML private TableView<ClassSession> classTable;
    @FXML private TableColumn<ClassSession, Long> idColumn;
    @FXML private TableColumn<ClassSession, String> courseColumn;
    @FXML private TableColumn<ClassSession, String> subjectColumn;
    @FXML private TableColumn<ClassSession, LocalDateTime> startTimeColumn;
    @FXML private TableColumn<ClassSession, LocalDateTime> endTimeColumn;
    @FXML private VBox rootPane;

    private ClassSessionService classSessionService = new ClassSessionService();
    private CourseService courseService = new CourseService();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue().getCourse();
            return new javafx.beans.property.SimpleStringProperty(course != null ? course.getCourseName() : "");
        });
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        courseComboBox.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        loadClasses();
        classTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                courseComboBox.getSelectionModel().select(newSelection.getCourse());
                subjectField.setText(newSelection.getSubject());
                startTimeField.setText(newSelection.getStartTime().format(formatter));
                endTimeField.setText(newSelection.getEndTime().format(formatter));
            }
        });
    }

    @FXML
    private void addClass() {
        try {
            ClassSession classSession = new ClassSession();
            classSession.setCourse(courseComboBox.getSelectionModel().getSelectedItem());
            classSession.setSubject(subjectField.getText());
            classSession.setStartTime(LocalDateTime.parse(startTimeField.getText(), formatter));
            classSession.setEndTime(LocalDateTime.parse(endTimeField.getText(), formatter));
            classSessionService.saveClassSession(classSession);
            loadClasses();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Class added successfully!");
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error adding class: " + e.getMessage());
        }
    }

    @FXML
    private void updateClass() {
        ClassSession selected = classTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                selected.setCourse(courseComboBox.getSelectionModel().getSelectedItem());
                selected.setSubject(subjectField.getText());
                selected.setStartTime(LocalDateTime.parse(startTimeField.getText(), formatter));
                selected.setEndTime(LocalDateTime.parse(endTimeField.getText(), formatter));
                classSessionService.saveClassSession(selected);
                loadClasses();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Class updated successfully!");
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error updating class: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a class to update!");
        }
    }

    @FXML
    private void deleteClass() {
        ClassSession selected = classTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            classSessionService.deleteClassSession(selected.getId());
            loadClasses();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Class deleted successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a class to delete!");
        }
    }

    private void loadClasses() {
        classTable.setItems(FXCollections.observableArrayList(classSessionService.getAllClassSessions()));
    }

    private void clearFields() {
        courseComboBox.getSelectionModel().clearSelection();
        subjectField.clear();
        startTimeField.clear();
        endTimeField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
