package com.example.rms.controller;

import com.example.rms.Tm.PurchaseTm;
import com.example.rms.dto.Purchasedto;
import com.example.rms.model.PurchaseModel;
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
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PurchaseController implements Initializable {

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane PurchaseAPid;

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
    private TableColumn<PurchaseTm,Date> colPurchaseDate;

    @FXML
    private TableColumn<PurchaseTm, String> colPurchaseID;

    @FXML
    private TableColumn<PurchaseTm, String> colSupplierID;

    @FXML
    private TableColumn<PurchaseTm, Double> colTotalAmount;

    @FXML
    private TableView<PurchaseTm> tblPurchase;

    @FXML
    private TextField txtPuirchaseDate;

    @FXML
    private TextField txtPurchaseID;

    @FXML
    private TextField txtSupplierID;

    @FXML
    private TextField txtTotalAmount;

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtPuirchaseDate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPurchaseID.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colSupplierID.setCellValueFactory(new PropertyValueFactory<>("supplierId"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("purchaseTotalAmount"));
        colPurchaseDate.setCellValueFactory(new PropertyValueFactory<>("purchaseDate"));

        try {
            txtPurchaseID.setText(PurchaseModel.getNextPurchaseId());
            refreshPage();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        showDate();
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtPurchaseID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtSupplierID.requestFocus(); // Move focus to the Name field
            }
        });

        txtSupplierID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtTotalAmount.requestFocus(); // Move focus to the Address field
            }
        });

        txtTotalAmount.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPuirchaseDate.requestFocus(); // Move focus to the Phone field
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

        txtPuirchaseDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }


    @FXML
    void BackOA(ActionEvent event) throws IOException {
        PurchaseAPid.getChildren().clear();
        PurchaseAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String purchaseId = txtPurchaseID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this purchase?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = PurchaseModel.deletePurchase(purchaseId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Purchase deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete purchase!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        // Code to generate a report can be added here
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        Purchasedto purchase = new Purchasedto(
                txtPurchaseID.getText(),
                txtSupplierID.getText(),
                Double.parseDouble(txtTotalAmount.getText()),
                Date.valueOf(txtPuirchaseDate.getText())
        );

        boolean isSaved = PurchaseModel.savePurchase(purchase);
        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Purchase saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save purchase!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        Purchasedto purchase = new Purchasedto(
                txtPurchaseID.getText(),
                txtSupplierID.getText(),
                Double.parseDouble(txtTotalAmount.getText()),
                Date.valueOf(txtPuirchaseDate.getText())
        );

        boolean isUpdated = PurchaseModel.updatePurchase(purchase);
        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Purchase updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update purchase!").show();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        PurchaseTm selectedPurchase = tblPurchase.getSelectionModel().getSelectedItem();
        if (selectedPurchase != null) {
            txtPurchaseID.setText(selectedPurchase.getPurchaseId());
            txtSupplierID.setText(selectedPurchase.getSupplierId());
            txtTotalAmount.setText(String.valueOf(selectedPurchase.getPurchaseTotalAmount()));
            txtPuirchaseDate.setText(String.valueOf(selectedPurchase.getPurchaseDate()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtPurchaseID.setText(PurchaseModel.getNextPurchaseId());
        txtSupplierID.clear();
        txtTotalAmount.clear();
        txtPuirchaseDate.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Purchasedto> purchases = PurchaseModel.getAllPurchases();
        ObservableList<PurchaseTm> purchaseTMs = FXCollections.observableArrayList();

        for (Purchasedto purchase : purchases) {
            purchaseTMs.add(new PurchaseTm(
                    purchase.getPurchaseId(),
                    purchase.getSupplierId(),
                    purchase.getPurchaseTotalAmount(),
                    purchase.getPurchaseDate()
            ));
        }
        tblPurchase.setItems(purchaseTMs);
    }

    @FXML
    private TextField txtSearchPurchaseID; // TextField for searching Purchase by ID

    @FXML
    private Button btnSearchPurchase; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String purchaseId = txtSearchPurchaseID.getText(); // Get the Purchase ID entered in the text field

        // Reset border color for the search field
        txtSearchPurchaseID.setStyle(txtSearchPurchaseID.getStyle() + ";-fx-border-color: #7367F0;");

        if (purchaseId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Purchase ID to search!").show();
            txtSearchPurchaseID.setStyle(txtSearchPurchaseID.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the purchase using the PurchaseModel
        Purchasedto purchase = PurchaseModel.searchPurchase(purchaseId);

        if (purchase != null) {
            // Populate the fields with purchase details
            txtPurchaseID.setText(purchase.getPurchaseId());
            txtSupplierID.setText(purchase.getSupplierId());
            txtTotalAmount.setText(String.valueOf(purchase.getPurchaseTotalAmount()));
            txtPuirchaseDate.setText(purchase.getPurchaseDate().toString());

            // Disable the Save button and enable Update/Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            // Display error if purchase is not found
            new Alert(Alert.AlertType.ERROR, "Purchase not found!").show();
            refreshPage();
        }
    }

}
