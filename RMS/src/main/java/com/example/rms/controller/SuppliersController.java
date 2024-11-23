package com.example.rms.controller;

import com.example.rms.Tm.SuppliersTm;
import com.example.rms.dto.Suupliersdto;
import com.example.rms.model.SuppliersModel;
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

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SuppliersController implements Initializable {

    @FXML
    private Button BackId;

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane SuppliersApid;

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
    private TableColumn<SuppliersTm, String> colAddress;

    @FXML
    private TableColumn<SuppliersTm, String> colContactPerson;

    @FXML
    private TableColumn<SuppliersTm, String> colEmail;

    @FXML
    private TableColumn<SuppliersTm, String> colName;

    @FXML
    private TableColumn<SuppliersTm, String> colPhone;

    @FXML
    private TableColumn<SuppliersTm, String> colSupplierId;

    @FXML
    private TableColumn<SuppliersTm, String> colUserId;

    @FXML
    private TableView<SuppliersTm> tblSuppliers;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtContactPerson;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSupplierId;

    @FXML
    private TextField txtUserId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colSupplierId.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("supplierName"));
        colContactPerson.setCellValueFactory(new PropertyValueFactory<>("contactPerson"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("supplierPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("supplierEmail"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("supplierAddress"));
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));

        try {
            txtSupplierId.setText(SuppliersModel.getNextSupplierId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtSupplierId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus(); // Move focus to the Name field
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtContactPerson.requestFocus(); // Move focus to the Address field
            }
        });

        txtContactPerson.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtEmail.requestFocus(); // Move focus to the Phone field
            }
        });

        txtEmail.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAddress.requestFocus(); // Move focus to the Email field
            }
        });

        txtAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtUserId.requestFocus(); // Move focus to the User ID field
            }
        });

        txtUserId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        SuppliersApid.getChildren().clear();
        SuppliersApid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String supplierId = txtSupplierId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this supplier?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = SuppliersModel.deleteSupplier(supplierId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete supplier!").show();
            }
        }

    }

    @FXML
    void GenerateOA(ActionEvent event) {

    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();

    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        // Retrieve input values
        String supplierId = txtSupplierId.getText();
        String name = txtName.getText();
        String contactPerson = txtContactPerson.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        String userId = txtUserId.getText();

        // Define validation patterns for other fields
        String namePattern = "^[A-Za-z ]{1,100}$"; // Name: only letters, up to 100 characters
        String contactPersonPattern = "^[A-Za-z ]{1,100}$"; // Contact Person: only letters, up to 100 characters
        String phonePattern = "^\\d{10,15}$"; // Phone: 10-15 digits
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        String addressPattern = "^.{1,255}$"; // Address: any characters, up to 255

        // Validate other fields
        boolean isValidName = name.matches(namePattern);
        boolean isValidContactPerson = contactPerson.matches(contactPersonPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidEmail = email.matches(emailPattern);
        boolean isValidAddress = address.matches(addressPattern);

        // Reset styles for input fields
        txtName.setStyle(txtName.getStyle() +"-fx-border-color: #7367F0;");
        txtContactPerson.setStyle(txtContactPerson.getStyle() + "-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + "-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color: #7367F0;");
        txtAddress.setStyle(txtAddress.getStyle() + "-fx-border-color: #7367F0;");

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidContactPerson) {
            txtContactPerson.setStyle(txtContactPerson.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtAddress.setStyle(txtAddress.getStyle() + "-fx-border-color: red;");
        }

        // If all validations pass (excluding SupplierId), save the supplier
        if (isValidName && isValidContactPerson && isValidPhone && isValidEmail && isValidAddress) {
            Suupliersdto supplier = new Suupliersdto(
                    supplierId, // SupplierId is not validated
                    name,
                    contactPerson,
                    phone,
                    email,
                    address,
                    userId
            );

            boolean isSaved = SuppliersModel.saveSupplier(supplier);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Supplier saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save supplier!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input detected. Please correct the highlighted fields.").show();
        }
    }


    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        Suupliersdto supplier = new Suupliersdto(
                txtSupplierId.getText(),
                txtName.getText(),
                txtContactPerson.getText(),
                txtPhone.getText(),
                txtEmail.getText(),
                txtAddress.getText(),
                txtUserId.getText()
        );

        boolean isUpdated = SuppliersModel.updateSupplier(supplier);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Supplier updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update supplier!").show();
        }

    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        SuppliersTm selectedItem = tblSuppliers.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtSupplierId.setText(selectedItem.getSupplierId());
            txtName.setText(selectedItem.getSupplierName());
            txtContactPerson.setText(selectedItem.getContactPerson());
            txtPhone.setText(selectedItem.getSupplierPhone());
            txtEmail.setText(selectedItem.getSupplierEmail());
            txtAddress.setText(selectedItem.getSupplierAddress());
            txtUserId.setText(selectedItem.getUserId());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtSupplierId.setText(SuppliersModel.getNextSupplierId());
        txtName.clear();
        txtContactPerson.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtAddress.clear();
        txtUserId.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Suupliersdto> suppliers = SuppliersModel.getAllSuppliers();
        ObservableList<SuppliersTm> supplierTMs = FXCollections.observableArrayList();

        for (Suupliersdto supplier : suppliers) {
            supplierTMs.add(new SuppliersTm(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getContactPerson(),
                    supplier.getSupplierPhone(),
                    supplier.getSupplierEmail(),
                    supplier.getSupplierAddress(),
                    supplier.getUserId()
            ));
        }
        tblSuppliers.setItems(supplierTMs);
    }

    @FXML
    private TextField txtSearchSupplierId;  // Add this for the search input

    @FXML
    private Button btnSearch;  // Add this for the search button

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String supplierId = txtSearchSupplierId.getText(); // Get the Supplier ID entered in the text field

        if (supplierId.isEmpty()) {
            new Alert(Alert.AlertType.WARNING, "Please enter a Supplier ID to search!").show();
            return;
        }

        // Search for the supplier using SuppliersModel
        Suupliersdto supplier = SuppliersModel.searchSupplier(supplierId);

        if (supplier != null) {
            // Populate the fields with the search result
            txtSupplierId.setText(supplier.getSupplierId());
            txtName.setText(supplier.getSupplierName());
            txtContactPerson.setText(supplier.getContactPerson());
            txtPhone.setText(supplier.getSupplierPhone());
            txtEmail.setText(supplier.getSupplierEmail());
            txtAddress.setText(supplier.getSupplierAddress());
            txtUserId.setText(supplier.getUserId());

            // Disable the Save button and enable Update and Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            new Alert(Alert.AlertType.ERROR, "Supplier not found!").show();
            refreshPage();
        }
    }

}



