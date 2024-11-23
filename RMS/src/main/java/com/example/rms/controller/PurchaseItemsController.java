package com.example.rms.controller;

import com.example.rms.Tm.PurchaseItemsTm;
import com.example.rms.dto.PurchaseItmsdto;
import com.example.rms.model.PurchaseItemsModel;
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

public class PurchaseItemsController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private AnchorPane PurchaseItemsAPid;

    @FXML
    private Button Saveid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<PurchaseItemsTm, String> colInventoryItemIID;

    @FXML
    private TableColumn<PurchaseItemsTm, Double> colPrice;

    @FXML
    private TableColumn<PurchaseItemsTm, String> colPurchaseID;

    @FXML
    private TableColumn<PurchaseItemsTm, String> colPurchaseItemID;

    @FXML
    private TableColumn<PurchaseItemsTm, String> colQuantity;

    @FXML
    private TableColumn<PurchaseItemsTm, String> colStatus;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<PurchaseItemsTm> tblPurchaseItems;

    @FXML
    private TextField txtInventoryItemId;

    @FXML
    private TextField txtPrice;

    @FXML
    private TextField txtPurchaseId;

    @FXML
    private TextField txtPurchaseItemId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtStatus;

    @FXML
    private Button updateid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colPurchaseItemID.setCellValueFactory(new PropertyValueFactory<>("purchaseItemId"));
        colInventoryItemIID.setCellValueFactory(new PropertyValueFactory<>("inventoryItemId"));
        colPurchaseID.setCellValueFactory(new PropertyValueFactory<>("purchaseId"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("purchasePrice"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        try {
            txtPurchaseItemId.setText(PurchaseItemsModel.getNextPurchaseItemId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        PurchaseItemsAPid.getChildren().clear();
        PurchaseItemsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String purchaseItemId = txtPurchaseItemId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = PurchaseItemsModel.deletePurchaseItem(purchaseItemId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete item!").show();
            }
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        PurchaseItemsTm selectedItem = tblPurchaseItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtPurchaseItemId.setText(selectedItem.getPurchaseItemId());
            txtInventoryItemId.setText(selectedItem.getInventoryItemId());
            txtPurchaseId.setText(selectedItem.getPurchaseId());
            txtPrice.setText(String.valueOf(selectedItem.getPurchasePrice()));
            txtQuantity.setText(selectedItem.getQuantity());
            txtStatus.setText(selectedItem.getStatus());

            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        }
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException {
        PurchaseItmsdto purchaseItem = new PurchaseItmsdto(
                txtPurchaseItemId.getText(),
                txtInventoryItemId.getText(),
                txtPurchaseId.getText(),
                Double.parseDouble(txtPrice.getText()),
                txtQuantity.getText(),
                txtStatus.getText()
        );

        boolean isSaved = PurchaseItemsModel.savePurchaseItem(purchaseItem);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save item!").show();
        }
    }

    @FXML
    void generatereportOA(ActionEvent event) {
        // Add logic for report generation if needed.
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException {
        PurchaseItmsdto purchaseItem = new PurchaseItmsdto(
                txtPurchaseItemId.getText(),
                txtInventoryItemId.getText(),
                txtPurchaseId.getText(),
                Double.parseDouble(txtPrice.getText()),
                txtQuantity.getText(),
                txtStatus.getText()
        );

        boolean isUpdated = PurchaseItemsModel.updatePurchaseItem(purchaseItem);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update item!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtPurchaseItemId.setText(PurchaseItemsModel.getNextPurchaseItemId());
        txtInventoryItemId.clear();
        txtPurchaseId.clear();
        txtPrice.clear();
        txtQuantity.clear();
        txtStatus.clear();

        Saveid.setDisable(false);
        Deleteid.setDisable(true);
        updateid.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<PurchaseItmsdto> purchaseItems = PurchaseItemsModel.getAllPurchaseItems();
        ObservableList<PurchaseItemsTm> purchaseItemsTms = FXCollections.observableArrayList();

        for (PurchaseItmsdto purchaseItem : purchaseItems) {
            purchaseItemsTms.add(new PurchaseItemsTm(
                    purchaseItem.getPurchaseItemId(),
                    purchaseItem.getInventoryItemId(),
                    purchaseItem.getPurchaseId(),
                    purchaseItem.getPurchasePrice(),
                    purchaseItem.getQuantity(),
                    purchaseItem.getStatus()
            ));
        }
        tblPurchaseItems.setItems(purchaseItemsTms);
    }
    @FXML
    private TextField txtSearchPurchaseItemId; // TextField for searching Purchase Item by ID

    @FXML
    private Button btnSearchPurchaseItem; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String purchaseItemId = txtSearchPurchaseItemId.getText(); // Get the Purchase Item ID entered in the text field

        // Reset border color for the search field
        txtSearchPurchaseItemId.setStyle(txtSearchPurchaseItemId.getStyle() + ";-fx-border-color: #7367F0;");

        if (purchaseItemId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Purchase Item ID to search!").show();
            txtSearchPurchaseItemId.setStyle(txtSearchPurchaseItemId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the purchase item using the PurchaseItemsModel
        PurchaseItmsdto purchaseItem = PurchaseItemsModel.searchPurchaseItem(purchaseItemId);

        if (purchaseItem != null) {
            // Populate the fields with the search result
            txtPurchaseItemId.setText(purchaseItem.getPurchaseItemId());
            txtInventoryItemId.setText(purchaseItem.getInventoryItemId());
            txtPurchaseId.setText(purchaseItem.getPurchaseId());
            txtPrice.setText(String.valueOf(purchaseItem.getPurchasePrice()));
            txtQuantity.setText(purchaseItem.getQuantity());
            txtStatus.setText(purchaseItem.getStatus());

            // Disable the Save button and enable Update and Delete buttons
            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        } else {
            // Display error if purchase item is not found
            new Alert(Alert.AlertType.ERROR, "Purchase item not found!").show();
            refreshPage();
        }
    }
    private void setupEnterKeyListeners() {
        txtPurchaseItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtInventoryItemId.requestFocus(); // Move focus to the Name field
            }
        });

        txtInventoryItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPurchaseId.requestFocus(); // Move focus to the Address field
            }
        });

        txtPurchaseId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus(); // Move focus to the Phone field
            }
        });

        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQuantity.requestFocus(); // Move focus to the Email field
            }
        });

//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtQuantity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }


}
