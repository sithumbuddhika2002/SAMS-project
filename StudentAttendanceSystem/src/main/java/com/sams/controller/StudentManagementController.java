
package com.sams.controller;

import com.sams.model.Course;
import com.sams.model.Student;
import com.sams.service.CourseService;
import com.sams.service.StudentService;
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

public class StudentManagementController {
    @FXML private TextField regNumberField;
    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private ComboBox<Course> courseComboBox;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, Long> idColumn;
    @FXML private TableColumn<Student, String> regNumberColumn;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> contactColumn;
    @FXML private TableColumn<Student, String> courseColumn;
    @FXML private VBox rootPane;

    private StudentService studentService = new StudentService();
    private CourseService courseService = new CourseService();

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        regNumberColumn.setCellValueFactory(new PropertyValueFactory<>("registrationNumber"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        courseColumn.setCellValueFactory(cellData -> {
            Course course = cellData.getValue().getCourse();
            return new javafx.beans.property.SimpleStringProperty(course != null ? course.getCourseName() : "");
        });
        courseComboBox.setItems(FXCollections.observableArrayList(courseService.getAllCourses()));
        loadStudents();
        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                regNumberField.setText(newSelection.getRegistrationNumber());
                nameField.setText(newSelection.getName());
                contactField.setText(newSelection.getContact());
                courseComboBox.getSelectionModel().select(newSelection.getCourse());
            }
        });
    }

    @FXML
    private void addStudent() {
        Student student = new Student();
        student.setRegistrationNumber(regNumberField.getText());
        student.setName(nameField.getText());
        student.setContact(contactField.getText());
        student.setCourse(courseComboBox.getSelectionModel().getSelectedItem());
        studentService.saveStudent(student);
        loadStudents();
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Student added successfully!");
    }

    @FXML
    private void updateStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setRegistrationNumber(regNumberField.getText());
            selected.setName(nameField.getText());
            selected.setContact(contactField.getText());
            selected.setCourse(courseComboBox.getSelectionModel().getSelectedItem());
            studentService.saveStudent(selected);
            loadStudents();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Student updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to update!");
        }
    }

    @FXML
    private void deleteStudent() {
        Student selected = studentTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            studentService.deleteStudent(selected.getId());
            loadStudents();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Student deleted successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a student to delete!");
        }
    }

    private void loadStudents() {
        studentTable.setItems(FXCollections.observableArrayList(studentService.getAllStudents()));
    }

    private void clearFields() {
        regNumberField.clear();
        nameField.clear();
        contactField.clear();
        courseComboBox.getSelectionModel().clearSelection();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
