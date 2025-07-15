package com.sams.controller;

import com.sams.model.User;
import com.sams.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Alert;

public class LoginController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    private UserService userService = new UserService();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        User user = userService.login(username, password);
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Login successful! Role: " + user.getRole());
            alert.showAndWait();
            // Navigate to appropriate dashboard based on role
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid credentials!");
            alert.showAndWait();
        }
    }
}