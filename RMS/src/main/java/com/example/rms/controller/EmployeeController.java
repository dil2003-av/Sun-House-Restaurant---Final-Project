package com.example.rms.controller;

import com.example.rms.Tm.EmployeeTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.EmployeeBO;
import com.example.rms.dto.Employeedto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.entity.Employee;
import com.example.rms.model.EmployeeModel;
import com.example.rms.model.PaymentsModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {

    @FXML
    private AnchorPane EmployeeAPid;

    @FXML
    private Label Employeeid;

    @FXML
    private Button Logoutid;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnSave;

    @FXML
    private Button btnUpdate;

    @FXML
    private Button btngeneratereport;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtEmployee;

    @FXML
    private TextField txtHiredate;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPosition;

    @FXML
    private TableColumn<EmployeeTm, String> colEmail;

    @FXML
    private TableColumn<EmployeeTm, String> colEmployeeID;

    @FXML
    private TableColumn<EmployeeTm, String> colHiredate;

    @FXML
    private TableColumn<EmployeeTm, String > colName;

    @FXML
    private TableColumn<EmployeeTm, String> colPhone;

    @FXML
    private TableColumn<EmployeeTm, String> colPosition;

    @FXML
    private TableView<EmployeeTm> tblEmployee;


    public EmployeeBO employeeBO = (EmployeeBO) BOFactory.getInstance().getBO(BOFactory.BOType.EMPLOYEE);


    @FXML
    void DeleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String employeeId = txtEmployee.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this employee?", ButtonType.YES, ButtonType.NO);
        Optional<ButtonType> buttonType = alert.showAndWait();

        // Check if the user clicked on the YES button
        if (buttonType.isPresent() && buttonType.get() == ButtonType.YES) {
            boolean isDeleted = employeeBO.deleteEmployee(employeeId);

            // Show appropriate message based on deletion success
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee deleted successfully!").show();
                refreshPage(); // Refresh the page after successful deletion
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete employee!").show();
            }
        }

    }

    @FXML
    void GenerateOA(ActionEvent event) {

    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
       refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtEmployee.getText();
        String name = txtName.getText();
        String position = txtPosition.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String hireDateStr = txtHiredate.getText(); // Changed variable name for clarity

        String namePattern = "^[A-Za-z ]{1,100}$"; // Name: letters and spaces, up to 100 characters
        String positionPattern = "^[A-Za-z ]{1,50}$"; // Position: letters and spaces, up to 50 characters
        String phonePattern = "^(\\d{10,15})$"; // Phone: 10 to 15 digits
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; // Email: standard pattern

        boolean isValidName = name.matches(namePattern);
        boolean isValidPosition = position.matches(positionPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidEmail = email.matches(emailPattern);

        // Resetting styles for borders
        resetBorderStyles();

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPosition) {
            txtPosition.setStyle(txtPosition.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }

        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (isValidName && isValidPosition && isValidPhone && isValidEmail) {
            Date hireDate = parseDate(hireDateStr); // Use java.sql.Date
            Employeedto employeeDTO = new Employeedto(
                    id.trim(),
                    name.trim(),
                    position.trim(),
                    phone.trim(),
                    email.trim(),
                    hireDate
            );

            //Employeedto Employeedto = null;
            employeeBO.saveEmployee(new Employeedto(  id, name, position, phone, email, hireDate));
            boolean isSaved = employeeBO.saveEmployee(employeeDTO);
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Employee saved...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save employee...!").show();
            }
        }
    }

    private Date parseDate(String dateStr) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(dateStr, formatter);
        return Date.valueOf(localDate); // Convert LocalDate to java.sql.Date
    }

    private void resetBorderStyles() {
        // Reset border styles to default
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #0578;");
        txtPosition.setStyle(txtPosition.getStyle() + ";-fx-border-color: #0578;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #0578;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #0578;");
    }

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtHiredate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtEmployee.getText();
        String name = txtName.getText();
        String position = txtPosition.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String hireDateStr = txtHiredate.getText(); // Changed variable name for clarity

        String namePattern = "^[A-Za-z ]{1,100}$"; // Name: letters and spaces, up to 100 characters
        String positionPattern = "^[A-Za-z ]{1,50}$"; // Position: letters and spaces, up to 50 characters
        String phonePattern = "^(\\d{10,15})$"; // Phone: 10 to 15 digits
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"; // Email: standard pattern

        boolean isValidName = name.matches(namePattern);
        boolean isValidPosition = position.matches(positionPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidEmail = email.matches(emailPattern);

        // Resetting styles for borders
        resetBorderStyles();

        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPosition) {
            txtPosition.setStyle(txtPosition.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }
        if (isValidName && isValidPhone && isValidPosition && isValidEmail) {
            Date hireDate = parseDate(hireDateStr); // Use java.sql.Date
            Employeedto employeeDTO = new Employeedto(
                    id.trim(),
                    name.trim(),
                    position.trim(),
                    phone.trim(),
                    email.trim(),
                    hireDate
            );
            employeeBO.updateEmployee(new Employeedto(  id, name, position, phone, email, hireDate));
            boolean isUpdate = employeeBO.updateEmployee(employeeDTO);
            if (isUpdate) {
                new Alert(Alert.AlertType.INFORMATION, "Employee updated...!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update employee...!").show();
            }
        }


    }

    @FXML
    void logoutOA(ActionEvent event) throws IOException {
        EmployeeAPid.getChildren().clear();
        EmployeeAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPosition.setCellValueFactory(new PropertyValueFactory<>("position"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colHiredate.setCellValueFactory(new PropertyValueFactory<>("hireDate"));

        //showDate();

        try {
            String nextEmployeeId = EmployeeModel.getNextEmployeeId();
            System.out.println(nextEmployeeId);
            txtEmployee.setText(nextEmployeeId);
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        showDate();
        setupEnterKeyListeners();
    }


    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();

        String nextEmployeeId = EmployeeModel.getNextEmployeeId();
        System.out.println(nextEmployeeId);
        txtEmployee.setText(nextEmployeeId);

        txtName.setText("");
        txtPosition.setText("");
        txtPhone.setText("");
        txtEmail.setText("");

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<Employeedto> employeeDTOS = EmployeeModel.getAllEmployee();
        ObservableList<EmployeeTm> employeeTMS = FXCollections.observableArrayList();

        for (Employeedto employeeDTO : employeeDTOS) {
            //EmployeeTM employeeTM = new EmployeeTM();

            EmployeeTm employeeTM = new EmployeeTm(
                    employeeDTO.getId(),
                    employeeDTO.getName(),
                    employeeDTO.getPosition(),
                    employeeDTO.getPhone(),
                    employeeDTO.getEmail(),
                    employeeDTO.getHireDate()
            );

            employeeTMS.add(employeeTM);
        }
        tblEmployee.setItems(employeeTMS);
    }


    @FXML
    void OnClickTable(MouseEvent event) {
        EmployeeTm selectedItem = tblEmployee.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtEmployee.setText(selectedItem.getId());
            txtName.setText(selectedItem.getName());
            txtPosition.setText(selectedItem.getPosition());
            txtPhone.setText(selectedItem.getPhone());
            txtEmail.setText(selectedItem.getEmail());
            txtHiredate.setText(selectedItem.getHireDate().toString()); // Updated to display the date

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }

    }
    @FXML
    private TextField txtSearchEmployeeId;

    @FXML
    private Button btnSearchEmployee; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws Exception {
        String employeeId = txtSearchEmployeeId.getText();

        // Reset border color for the search field
        txtSearchEmployeeId.setStyle(txtSearchEmployeeId.getStyle() + ";-fx-border-color: #7367F0;");

        if (employeeId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Employee ID to search!").show();
            txtSearchEmployeeId.setStyle(txtSearchEmployeeId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        Employeedto employee = EmployeeModel.searchEmployee(employeeId);


        if (employee != null) {
            txtEmployee.setText(employee.getId());
            txtName.setText(employee.getName());
            txtPosition.setText(employee.getPosition());
            txtPhone.setText(employee.getPhone());
            txtEmail.setText(employee.getEmail());
            txtHiredate.setText(employee.getHireDate().toString()); // Format the date properly


            // Disable the Save button and enable Update/Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            // Display error if payment is not found
            new Alert(Alert.AlertType.ERROR, "Payment not found!").show();
            refreshPage();
        }
    }
    private void setupEnterKeyListeners() {

        txtEmployee.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus(); // Move focus to the Name field
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPosition.requestFocus(); // Move focus to the Address field
            }
        });

        txtPosition.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPhone.requestFocus(); // Move focus to the Phone field
            }
        });

        txtPhone.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus(); // Move focus to the Email field
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtHiredate.requestFocus(); // Move focus to the User ID field
            }
        });

        txtHiredate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }




}
