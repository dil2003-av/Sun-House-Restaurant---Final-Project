package com.example.rms.controller;

import com.example.rms.Tm.UserTm;
import com.example.rms.dto.Userdto;
import com.example.rms.model.UserModel;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static com.example.rms.model.TablesAssignmentsModel.getNextTableAssignmentId;
import static com.example.rms.model.UserModel.getNextUserId;

public class UserController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private Button Saveid;

    @FXML
    private AnchorPane UserAPid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<UserTm, LocalDateTime> colLogintime;

    @FXML
    private TableColumn<UserTm, String> colUsername;

    @FXML
    private TableColumn<UserTm, String> colemployeeid;

    @FXML
    private TableColumn<UserTm, String> colpassword;

    @FXML
    private TableColumn<UserTm, String> coluserid;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<UserTm> tbluser;

    @FXML
    private TextField txtEmployeeid;

    @FXML
    private TextField txtUserid;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtlogintime;

    @FXML
    private TextField txtpassword;

    @FXML
    private Button updateid;

    private void showCurrentDateTime() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Creating a Timeline to update the time every second
        Timeline timeTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            txtlogintime.setText(now.format(dateTimeFormatter));  // Update the text field with current date and time
        }));

        timeTimeline.setCycleCount(Animation.INDEFINITE);  // Infinite loop to keep updating
        timeTimeline.play();  // Start the timeline
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            txtUserid.setText(getNextUserId());
            refreshTable();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Saveid.setDisable(false);
        updateid.setDisable(true);
        Deleteid.setDisable(true);

        initializeTable();
        showCurrentDateTime();
        setupEnterKeyListeners();
    }

    private void initializeTable() {
        coluserid.setCellValueFactory(new PropertyValueFactory<>("id"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("name"));
        colpassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colLogintime.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
        colemployeeid.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
    }

    private void setupEnterKeyListeners() {
        txtUserid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtUsername.requestFocus(); // Move focus to the Name field
            }
        });

        txtUsername.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtpassword.requestFocus(); // Move focus to the Address field
            }
        });

        txtpassword.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtlogintime.requestFocus(); // Move focus to the Phone field
            }
        });

        txtlogintime.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmployeeid.requestFocus(); // Move focus to the Email field
            }
        });

//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtEmployeeid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        UserAPid.getChildren().clear();
        UserAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String userId = txtUserid.getText();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this User?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();
        if (buttonType.get() == ButtonType.YES) {
            boolean isDeleted = UserModel.deleteUser(userId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "User Deleted...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Fail to delete User...!").show();
            }
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        UserTm selectedUser = tbluser.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            txtUserid.setText(selectedUser.getId());
            txtUsername.setText(selectedUser.getName());
            txtpassword.setText(selectedUser.getPassword());
            txtlogintime.setText(selectedUser.getLoginTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            txtEmployeeid.setText(selectedUser.getEmployeeId());

            Saveid.setDisable(true);
            updateid.setDisable(false);
            Deleteid.setDisable(false);
        }
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) {
        try {
            String loginTimeString = txtlogintime.getText().trim();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime loginTime = LocalDateTime.parse(loginTimeString, formatter);

            Userdto user = new Userdto(
                    txtUserid.getText(),
                    txtUsername.getText(),
                    txtpassword.getText(),
                    loginTime,
                    txtEmployeeid.getText()
            );

            boolean isSaved = UserModel.saveUser(user);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "User Saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save User...!").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR, "Invalid data. Please check your inputs and try again.").show();
        }
    }

    @FXML
    void generatereportOA(ActionEvent event) {
        // Implement report generation logic
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException {
        String userId = txtUserid.getText();
        String username = txtUsername.getText();
        String password = txtpassword.getText();
        String employeeId = txtEmployeeid.getText();

        LocalDateTime loginTime = LocalDateTime.parse(txtlogintime.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        Userdto user = new Userdto(userId, username, password, loginTime, employeeId);

        boolean isUpdated = UserModel.updateUser(user);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "User updated...!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Fail to update User...!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
       // txtUserid.setText(getNextUserId());
        txtUserid.setText(getNextUserId());
        txtUsername.clear();
        txtpassword.clear();
        txtlogintime.clear();
        txtEmployeeid.clear();

        Saveid.setDisable(false);
        updateid.setDisable(true);
        Deleteid.setDisable(true);
    }

    private void refreshTable() {
        try {
            ArrayList<Userdto> users = UserModel.getAllUsers();
            ObservableList<UserTm> userTms = FXCollections.observableArrayList();

            for (Userdto user : users) {
                userTms.add(new UserTm(
                        user.getId(),
                        user.getName(),
                        user.getPassword(),
                        user.getLoginTime(),
                        user.getEmployeeId()
                ));
            }
            tbluser.setItems(userTms);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField txtSearchUserId;

    @FXML
    private Button btnSearch;

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String userId = txtSearchUserId.getText(); // Get the User ID entered in the search field

        if (userId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a User ID to search!").show();
            return;
        }


        Userdto user = UserModel.searchUser(userId);

        if (user != null) {
            // Populate the fields with the search result
            txtUserid.setText(user.getId());
            txtUsername.setText(user.getName());
            txtpassword.setText(user.getPassword());
            txtlogintime.setText(user.getLoginTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            txtEmployeeid.setText(user.getEmployeeId());

            // Disable the Save button and enable Update and Delete buttons
            Saveid.setDisable(true);
            updateid.setDisable(false);
            Deleteid.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "User not found!").show();
            refreshPage();
        }
    }

}
