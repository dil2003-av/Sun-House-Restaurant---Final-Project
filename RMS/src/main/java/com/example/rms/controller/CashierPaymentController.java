package com.example.rms.controller;

import com.example.rms.Tm.PaymentsTm;
import com.example.rms.db.DBConnection;
import com.example.rms.dto.Paymentsdto;
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
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class CashierPaymentController implements Initializable {

    @FXML
    private AnchorPane CashierPaymentApid;

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane PaymentsAPid;

    @FXML
    private Button btnBack;

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
    private TableColumn<PaymentsTm, String> colPaymentID;

    @FXML
    private TableColumn<PaymentsTm, String> colPaymentMethod;

    @FXML
    private TableColumn<PaymentsTm, Double> colPaymentAmount;

    @FXML
    private TableColumn<PaymentsTm, Date> colPaymentDate;

    @FXML
    private TableView<PaymentsTm> tblPayments;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtPaymentDate;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtPaymentMethod;

//    @FXML
//    void initialize() throws SQLException {
//       //initializeTable();
//        refreshTable();
//
//        txtPaymentId.setText(getNextOrderId());
////        // Format and set current date in "dd-MM-yyyy" format
//      txtPaymentDate.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//
////        // Disable update and delete buttons initially
//      btnUpdate.setDisable(true);
//       btnDelete.setDisable(true);
//   }

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtPaymentDate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPaymentID.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colPaymentMethod.setCellValueFactory(new PropertyValueFactory<>("paymentMethod"));
        colPaymentAmount.setCellValueFactory(new PropertyValueFactory<>("paymentAmount"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("paymentDate"));

        try {
            txtPaymentId.setText(PaymentsModel.getNextPaymentId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showDate();
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtPaymentId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPaymentMethod.requestFocus(); // Move focus to the Name field
            }
        });

        txtPaymentMethod.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtAmount.requestFocus(); // Move focus to the Address field
            }
        });

        txtAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPaymentDate.requestFocus(); // Move focus to the Phone field
            }
        });

//        txtPrice.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtCategory.requestFocus(); // Move focus to the Email field
//            }
//        });
//
//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtPaymentDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }



    @FXML
    void BackOA(ActionEvent event) throws IOException {
        CashierPaymentApid.getChildren().clear();
        CashierPaymentApid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/CashierHome.fxml")));
    }
    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String paymentId = txtPaymentId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this payment?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = PaymentsModel.deletePayment(paymentId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Payment deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete payment!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        Connection connection = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Payment.jrxml"));
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
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        Paymentsdto payment = new Paymentsdto(
                txtPaymentId.getText(),
                txtPaymentMethod.getText(),
                Double.parseDouble(txtAmount.getText()),
                Date.valueOf(txtPaymentDate.getText())
        );

        boolean isSaved = PaymentsModel.savePayment(payment);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Payment saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save payment!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        Paymentsdto payment = new Paymentsdto(
                txtPaymentId.getText(),
                txtPaymentMethod.getText(),
                Double.parseDouble(txtAmount.getText()),
                Date.valueOf(txtPaymentDate.getText())
        );

        boolean isUpdated = PaymentsModel.updatePayment(payment);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Payment updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update payment!").show();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        PaymentsTm selectedItem = tblPayments.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtPaymentId.setText(selectedItem.getPaymentId());
            txtPaymentMethod.setText(selectedItem.getPaymentMethod());
            txtAmount.setText(String.valueOf(selectedItem.getPaymentAmount()));
            txtPaymentDate.setText(selectedItem.getPaymentDate().toString());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtPaymentId.setText(PaymentsModel.getNextPaymentId());
        txtPaymentMethod.clear();
        txtAmount.clear();
        txtPaymentDate.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Paymentsdto> payments = PaymentsModel.getAllPayments();
        ObservableList<PaymentsTm> paymentTMs = FXCollections.observableArrayList();

        for (Paymentsdto payment : payments) {
            paymentTMs.add(new PaymentsTm(
                    payment.getPaymentId(),
                    payment.getPaymentMethod(),
                    payment.getPaymentAmount(),
                    payment.getPaymentDate()
            ));
        }
        tblPayments.setItems(paymentTMs);
    }
    @FXML
    private TextField txtSearchPaymentId; // TextField for searching Payment by ID

    @FXML
    private Button btnSearchPayment; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String paymentId = txtSearchPaymentId.getText(); // Get the Payment ID entered in the text field

        // Reset border color for the search field
        txtSearchPaymentId.setStyle(txtSearchPaymentId.getStyle() + ";-fx-border-color: #7367F0;");

        if (paymentId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Payment ID to search!").show();
            txtSearchPaymentId.setStyle(txtSearchPaymentId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the payment using the PaymentsModel
        Paymentsdto payment = PaymentsModel.searchPayment(paymentId);

        if (payment != null) {
            // Populate the fields with payment details
            txtPaymentId.setText(payment.getPaymentId());
            txtPaymentMethod.setText(payment.getPaymentMethod());
            txtAmount.setText(String.valueOf(payment.getPaymentAmount()));
            txtPaymentDate.setText(payment.getPaymentDate().toString());

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

}
