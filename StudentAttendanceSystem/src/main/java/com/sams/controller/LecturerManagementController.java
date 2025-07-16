
package com.sams.controller;

import com.sams.model.Lecturer;
import com.sams.service.LecturerService;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.util.Duration;
import javafx.scene.layout.VBox;

public class LecturerManagementController {
    @FXML private TextField nameField;
    @FXML private TextField contactField;
    @FXML private TableView<Lecturer> lecturerTable;
    @FXML private TableColumn<Lecturer, Long> idColumn;
    @FXML private TableColumn<Lecturer, String> nameColumn;
    @FXML private TableColumn<Lecturer, String> contactColumn;
    @FXML private VBox rootPane;

    private LecturerService lecturerService = new LecturerService();

    @FXML
    private void initialize() {
        FadeTransition fade = new FadeTransition(Duration.millis(1000), rootPane);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.play();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<>("contact"));
        loadLecturers();
        lecturerTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nameField.setText(newSelection.getName());
                contactField.setText(newSelection.getContact());
            }
        });
    }

    @FXML
    private void addLecturer() {
        Lecturer lecturer = new Lecturer();
        lecturer.setName(nameField.getText());
        lecturer.setContact(contactField.getText());
        lecturerService.saveLecturer(lecturer);
        loadLecturers();
        clearFields();
        showAlert(Alert.AlertType.INFORMATION, "Lecturer added successfully!");
    }

    @FXML
    private void updateLecturer() {
        Lecturer selected = lecturerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            selected.setName(nameField.getText());
            selected.setContact(contactField.getText());
            lecturerService.saveLecturer(selected);
            loadLecturers();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Lecturer updated successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a lecturer to update!");
        }
    }

    @FXML
    private void deleteLecturer() {
        Lecturer selected = lecturerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            lecturerService.deleteLecturer(selected.getId());
            loadLecturers();
            clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Lecturer deleted successfully!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Please select a lecturer to delete!");
        }
    }

    private void loadLecturers() {
        lecturerTable.setItems(FXCollections.observableArrayList(lecturerService.getAllLecturers()));
    }

    private void clearFields() {
        nameField.clear();
        contactField.clear();
    }

    private void showAlert(Alert.AlertType type, String message) {
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
