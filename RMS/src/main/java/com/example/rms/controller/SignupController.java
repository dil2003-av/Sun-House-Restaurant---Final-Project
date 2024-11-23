package com.example.rms.controller;

import com.example.rms.model.EmployeeModel;
import com.example.rms.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class SignupController {

    @FXML
    private TextField Emailid;

    @FXML
    private TextField Nameid;

    @FXML
    private Button SignUpid;

    @FXML
    private AnchorPane SignUppage;

    @FXML
    private PasswordField confirmid;

    @FXML
    private PasswordField passwordid;

    @FXML
    private TextField phoneid;


    @FXML
    private TextField positionid;

    @FXML
    private TextField usernameid;

    @FXML
    private RadioButton radioid;

    @FXML
    private Button loginid;

    private UserModel userModel = new UserModel();
    private EmployeeModel employeeModel = new EmployeeModel();

    @FXML
    void loginOA(ActionEvent event) throws IOException {
        SignUppage.getChildren().clear();
        SignUppage.getChildren().add(FXMLLoader.load(getClass().getResource("/view/Login.fxml")));
    }


    @FXML
    void SignupOA(ActionEvent event) throws SQLException {
        String userid = UserModel.getNextUserId();
        String employId = EmployeeModel.getNextEmployeeId();
        String phone = phoneid.getText();
        String position = positionid.getText();
        String username = usernameid.getText();
        String name = Nameid.getText();
        String email = Emailid.getText();
        String password = passwordid.getText();
        String confirmPassword = confirmid.getText();
        Date date = Date.valueOf(LocalDate.now());

        // Validation
        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() ||
                phone.isEmpty() || position.isEmpty() || username.isEmpty()) {
            new Alert(Alert.AlertType.ERROR, "All fields are required!").show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            new Alert(Alert.AlertType.ERROR, "Passwords do not match!").show();
            return;
        }

        if (!isValidEmail(email)) {
            new Alert(Alert.AlertType.ERROR, "Invalid email address!").show();
            return;
        }

        // Save employee
        boolean isEmploy = employeeModel.save(employId, name, position, phone, email, date);
        System.out.println(isEmploy);
        if (isEmploy) {
            // Save user
            boolean isSignUp = userModel.save(userid, username, password, employId);
            System.out.println(isSignUp);
            if (isSignUp) {
                new Alert(Alert.AlertType.INFORMATION, "Sign up successful!").show();
                try {
                    SignUppage.getChildren().clear();
                    AnchorPane loginPage = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
                    SignUppage.getChildren().add(loginPage);
                } catch (IOException e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, "Could not load the login page!").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "Sign up failed!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Could not save employee details!").show();
        }
    }


    @FXML
    public void initialize() {
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        Nameid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                usernameid.requestFocus(); // Move focus to the Name field
            }
        });

        usernameid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Emailid.requestFocus(); // Move focus to the Address field
            }
        });

        Emailid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                passwordid.requestFocus(); // Move focus to the Phone field
            }
        });

        passwordid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                phoneid.requestFocus(); // Move focus to the Email field
            }
        });

        phoneid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                confirmid.requestFocus(); // Move focus to the User ID field
            }
        });

        confirmid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                positionid.requestFocus(); // Move focus to the Email field
            }
        });

        positionid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                SignUpid.fire(); // Trigger Save button's action
            }
        });
    }


    // Email validation method
    private boolean isValidEmail(String email) {
        String emailValid = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        return email.matches(emailValid);
    }



//    private boolean isValidEmail(String email) {
//        return email.contains("@") && email.contains(".");
//    }

    @FXML
    void radioonAction(ActionEvent event) {
    }

}
