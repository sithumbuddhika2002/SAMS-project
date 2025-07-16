
package com.sams.controller;

import com.sams.model.Course;
import com.sams.service.CourseService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.util.Duration;

public class CourseManagementController {
    @FXML private TextField courseNameField;
    @FXML private TextField descriptionField;
    @FXML private TableView<Course> courseTable;
    @FXML private TableColumn<Course, Long> idColumn;
    @FXML private TableColumn<Course, String> nameColumn;
    @FXML private TableColumn<Course, String> descriptionColumn;
    @FXML private VBox rootPane;

    private CourseService courseService = new CourseService();

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        loadCourses();
        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                courseNameField.setText(newSelection.getCourseName());
                descriptionField.setText(newSelection.getDescription());
            }
        });
    }

    @FXML
    private void addCourse() {
        Course course = new Course();
        course.setCourseName(courseNameField.getText());
        course.setDescription(descriptionField.getText());
        courseService.saveCourse(course);
        loadCourses();
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Course added successfully!");
    }

    @FXML
    private void updateCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setCourseName(courseNameField.getText());
            selected.setDescription(descriptionField.getText());
            courseService.saveCourse(selected);
            loadCourses();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Course updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a course to update!");
        }
    }

    @FXML
    private void deleteCourse() {
        Course selected = courseTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            courseService.deleteCourse(selected.getId());
            loadCourses();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Course deleted successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a course to delete!");
        }
    }

    private void loadCourses() {
        courseTable.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
    }

    private void clearFields() {
        courseNameField.clear();
        descriptionField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
