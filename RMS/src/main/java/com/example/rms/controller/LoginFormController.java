package com.example.rms.controller;

//import javafx.animation.Animation;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
import com.example.rms.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LoginFormController {

    @FXML
    private AnchorPane APid;

    @FXML
    private Button Signupid;

    @FXML
    private AnchorPane loginPage;

    @FXML
    private Button loginid;

    @FXML
    private PasswordField txtpass;

    @FXML
    private TextField txtuser;

    @FXML
    private Label TimeID;

    private UserModel userModel = new UserModel();

    @FXML
    void PassonAction(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtuser.setOnAction(event -> txtpass.requestFocus());
        txtpass.setOnAction(event -> {
            try {
                loginOnAction(new ActionEvent());
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void SignOA(ActionEvent event) throws IOException {
        loginPage.getChildren().clear();
        loginPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/SignUppage.fxml")));

    }

    @FXML
    private Button btnForgotPassword;


    @FXML
    void ForgotPasswordOA(ActionEvent event) throws IOException {
        loginPage.getChildren().clear();
        loginPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/ForgotPassword.fxml")));

    }

    @FXML
    void loginOnAction(ActionEvent event) throws IOException, SQLException {
        String username = txtuser.getText().trim();
        String password = txtpass.getText().trim();

        // Validate input fields
        if (username.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Username cannot be empty!").show();
            txtuser.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Password cannot be empty!").show();
            txtpass.requestFocus();
            return;
        }

        // Fetch user details from the model
        String isUserName = userModel.getUserName(username);
        if (isUserName == null) {
            new Alert(Alert.AlertType.ERROR, "Username not found!").show();
            return;
        }

        String userId = userModel.getUserId(isUserName);
        if (userId == null) {
            new Alert(Alert.AlertType.ERROR, "Error retrieving user ID!").show();
            return;
        }

        String storedPassword = userModel.getPassword(userId);
        if (storedPassword == null) {
            new Alert(Alert.AlertType.ERROR, "Error retrieving password!").show();
            return;
        }

        // Validate username and password
        if (storedPassword.equals(password)) {
            if (username.equalsIgnoreCase("Manager")) {
                // Navigate to HomeController (Manager's view)
                loginPage.getChildren().clear();
                loginPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
            } else if (username.equalsIgnoreCase("Cashier")) {
                // Navigate to CashierHomeController (Cashier's view)
                loginPage.getChildren().clear();
                loginPage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierHome.fxml")));
            } else {
                // Default behavior for unknown users
                new Alert(Alert.AlertType.WARNING, "Access denied for this user!").show();
            }
        } else {
            // Invalid password
            new Alert(Alert.AlertType.ERROR, "Incorrect password!").show();
        }
        }



    @FXML
    void userOA(ActionEvent event) {

    }

}