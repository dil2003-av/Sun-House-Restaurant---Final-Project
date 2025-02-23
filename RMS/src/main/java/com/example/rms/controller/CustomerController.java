package com.example.rms.controller;

import com.example.rms.Tm.CustomerTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.CustomerBo;
import com.example.rms.db.DBConnection;
import com.example.rms.dto.Customerdto;
import com.example.rms.entity.Customer;
//import com.example.rms.model.CustomerModel;
import com.example.rms.model.CustomerModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {

    @FXML
    private AnchorPane customerAPid;

    @FXML
    private Button saveid;

    @FXML
    private Button Delete;

    @FXML
    private Button Reset;

    @FXML
    private Button Update;

    @FXML
    private Button Generatereport;

    @FXML
    private TableView<CustomerTm> tblcustomer;

    @FXML
    private TableColumn<CustomerTm, String> colCustomerId;

    @FXML
    private TableColumn<CustomerTm, String> colName;

    @FXML
    private TableColumn<CustomerTm, String> colPhone;

    @FXML
    private TableColumn<CustomerTm, String> colEmail;

    @FXML
    private TableColumn<CustomerTm, String> coladdress;

    @FXML
    private TableColumn<CustomerTm, String> coluserid;

    @FXML
    private TextField txtCustomerid;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtaddress;

    @FXML
    private TextField txtUserId;

//    private int currentIndex;

    public CustomerBo customerBO = (CustomerBo) BOFactory.getInstance().getBO(BOFactory.BOType.CUSTOMER);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Set up table columns
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("CustomerId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("CustomerPhone"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("CustomerEmail"));
        coladdress.setCellValueFactory(new PropertyValueFactory<>("CustomerAddress"));
        coluserid.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        try {
            // Initialize Customer ID and refresh the page
            txtCustomerid.setText(customerBO.getNextCustomerId());
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setupEnterKeyListeners();
    }
    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void logoutOA(ActionEvent event) throws IOException {
        customerAPid.getChildren().clear();
        customerAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));

    }

    private void setupEnterKeyListeners() {
        txtCustomerid.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus(); // Move focus to the Name field
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtaddress.requestFocus(); // Move focus to the Address field
            }
        });

        txtaddress.setOnKeyPressed(event -> {
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
                txtUserId.requestFocus(); // Move focus to the User ID field
            }
        });

        txtUserId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void saveOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtCustomerid.getText();
        String name = txtName.getText();
        String address = txtaddress.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String userid = txtUserId.getText();

        // Define patterns for validation
        String namePattern = "^[A-Za-z ]{1,100}$"; // Name: only letters, up to 100 characters
        String addressPattern = "^.{1,255}$"; // Address: any characters, up to 255
        String phonePattern = "^(\\d{10,15})$"; // Phone: 10-15 digits
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";


        // Validate the inputs
        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPhone = phone.matches(phonePattern); // Ensure phone has exactly 10 digits
        boolean isValidEmail = email.matches(emailPattern);

        // Set styles for invalid inputs
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtaddress.setStyle(txtaddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtaddress.setStyle(txtaddress.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        // If all fields are valid, proceed to save the customer
        if (isValidName && isValidAddress && isValidPhone && isValidEmail) {
            Customerdto customerDTO = new Customerdto(id, name, address,phone, email, userid);

            boolean isSaved=customerBO.saveCustomer(new Customerdto(id, name, address,phone, email, userid));

            //boolean isSaved = customerBO.saveCustomer(customerDTO);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Customer saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save customer.").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input detected. Please correct the highlighted fields.").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String id = txtCustomerid.getText();
        String name = txtName.getText();
        String address = txtaddress.getText();
        String phone = txtPhone.getText();
        String email = txtEmail.getText();
        String userid = txtUserId.getText();

        // Define patterns for validation
        String namePattern = "^[A-Za-z ]{1,100}$"; // Name: only letters, up to 100 characters
        String addressPattern = "^.{1,255}$"; // Address: any characters, up to 255
        String phonePattern = "^(\\d{10,15})$"; // Phone: 10-15 digits
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        // Validate the inputs
        boolean isValidName = name.matches(namePattern);
        boolean isValidAddress = address.matches(addressPattern);
        boolean isValidPhone = phone.matches(phonePattern);
        boolean isValidEmail = email.matches(emailPattern);

        // Reset styles for all fields
        txtName.setStyle(txtName.getStyle() + ";-fx-border-color: #7367F0;");
        txtaddress.setStyle(txtaddress.getStyle() + ";-fx-border-color: #7367F0;");
        txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: #7367F0;");
        txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: #7367F0;");

        // Highlight invalid fields
        if (!isValidName) {
            txtName.setStyle(txtName.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtaddress.setStyle(txtaddress.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidPhone) {
            txtPhone.setStyle(txtPhone.getStyle() + ";-fx-border-color: red;");
        }
        if (!isValidEmail) {
            txtEmail.setStyle(txtEmail.getStyle() + ";-fx-border-color: red;");
        }

        // If all fields are valid, proceed to update the customer
        if (isValidName && isValidAddress && isValidPhone && isValidEmail) {
            Customerdto customer = new Customerdto(id, name, address, phone, email, userid);

            boolean isUpdated=customerBO.updateCustomer(new Customerdto(id, name, address, phone, email, userid));
           // boolean isUpdated = CustomerModel.updateCustomer(customer);
            if (isUpdated) {
                new Alert(Alert.AlertType.INFORMATION, "Customer updated successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to update customer!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input detected. Please correct the highlighted fields.").show();
        }
    }


    @FXML
    void generateOA(ActionEvent event) {
        Connection connection = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/customers.jrxml"));
            connection = DBConnection.getInstance().getConnection();
            //<key/index,value> eka tamai string object widiyata danne
            Map<String,Object> parameters = new HashMap<>();
            parameters.put("today", LocalDate.now().toString());
            //map ekka wada karana kota duplicate karanne naaa

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
            JasperViewer.viewReport(jasperPrint); // Viewing the report
        } catch (JRException | SQLException e) {
            e.printStackTrace(); // Print the exception stack trace
//        } finally {
//            if (connection != null) {
//                try {
//                    connection.close(); // Ensure the connection is closed
//                } catch (SQLException e) {
//                    e.printStackTrace(); // Handle connection closing exception
//                }
//            }
        }

    }



    @FXML
    void deleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        // Get the customer ID from the text field
        String customerId = txtCustomerid.getText();

        // Show confirmation dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to delete this customer?",
                ButtonType.YES, ButtonType.NO);

        if (alert.showAndWait().get() == ButtonType.YES) {
            // Call deleteCustomer and store the result in a boolean variable
            boolean isDeleted = customerBO.deleteCustomer(customerId);

            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Customer deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete customer!").show();
            }
        }
    }


    @FXML
    void clickOnAction(MouseEvent event) {
        CustomerTm selectedItem = tblcustomer.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtCustomerid.setText(selectedItem.getCustomerId());
            txtName.setText(selectedItem.getCustomerName());
            txtaddress.setText(selectedItem.getCustomerAddress());
            txtPhone.setText(selectedItem.getCustomerPhone());
            txtEmail.setText(selectedItem.getCustomerEmail());
            txtUserId.setText(selectedItem.getUserId());

            saveid.setDisable(true);
            Delete.setDisable(false);
            Update.setDisable(false);
        }
    }

//    @FXML
//    void cmbCustomerOnAction(ActionEvent event) throws SQLException {
//        String selectedCustomerId = txtCustomerid.getSelectionModel().getSelectedItem();
//        Customerdto customerDTO = CustomerModel.findByCustomerId(selectedCustomerId);
//        lblCustomerName.setText(customerDTO.getName());
//    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        txtCustomerid.setText(customerBO.getNextCustomerId());
        refreshTable();
        txtName.clear();
        txtaddress.clear();
        txtPhone.clear();
        txtEmail.clear();
        txtUserId.clear();
        saveid.setDisable(false);
        Delete.setDisable(true);
        Update.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<Customerdto> customers = customerBO.getAllCustomers();
        ObservableList<CustomerTm> customerTMs = FXCollections.observableArrayList();

        for (Customerdto customer : customers) {
            customerTMs.add(new CustomerTm(
                    customer.getCustomerId(),
                    customer.getCustomerName(),
                    customer.getCustomerAddress(),
                    customer.getCustomerPhone(),
                    customer.getCustomerEmail(),
                    customer.getUserId()
            ));
        }
        tblcustomer.setItems(customerTMs);
    }

    @FXML
    private TextField txtSearchCustomerId;

    @FXML
    private Button btnSearch;

    public void setCustomerEmail(String email) {
        txtEmail.setText(email);
    }


    @FXML
    public void btnOpenMailSendModelOnAction(ActionEvent actionEvent) {
        CustomerTm selectedItem = tblcustomer.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            new Alert(Alert.AlertType.WARNING, "Please select customer..!");
            return;
        }

        try {
            // Load the mail dialog from FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/SendMail.fxml"));
            Parent load = loader.load();

            SendMailController sendMailController = loader.getController();

            String email = selectedItem.getCustomerEmail();
            sendMailController.setCustomerEmail(email);

            Stage stage = new Stage();
            stage.setScene(new Scene(load));
            stage.setTitle("Send email");

            // Set window as modal
            stage.initModality(Modality.APPLICATION_MODAL);

            // Get the window of the current scene
            Window underWindow = ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.initOwner(underWindow);

            stage.showAndWait();
        } catch (IOException e) {
            new Alert(Alert.AlertType.ERROR, "Fail to load UI..!");
            e.printStackTrace();
        }
    }


    @FXML
    void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {

        String customerId = txtSearchCustomerId.getText(); // Get the Customer ID entered in the search field

        // Reset border color for the search field
        txtSearchCustomerId.setStyle(txtSearchCustomerId.getStyle() + ";-fx-border-color: #7367F0;");

        if (customerId.isEmpty()) {
            // Display error if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Customer ID to search!").show();
            txtSearchCustomerId.setStyle(txtSearchCustomerId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the customer using CustomerModel
        Customerdto customer = CustomerModel.searchCustomer(customerId);

        if (customer != null) {
            // Populate the fields with customer details
            txtCustomerid.setText(customer.getCustomerId());
            txtName.setText(customer.getCustomerName());
            txtaddress.setText(customer.getCustomerAddress());
            txtPhone.setText(customer.getCustomerPhone());
            txtEmail.setText(customer.getCustomerEmail());
            txtUserId.setText(customer.getUserId());

            // Disable Save button and enable Update/Delete buttons
            saveid.setDisable(true);
            Delete.setDisable(false);
            Update.setDisable(false);
        } else {
            // Display warning if the customer is not found
            new Alert(Alert.AlertType.WARNING, "No customer found with ID: " + customerId).show();
            refreshPage();
        }
    }

}
