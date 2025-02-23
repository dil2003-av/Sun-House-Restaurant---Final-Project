package com.example.rms.controller;

import com.example.rms.Tm.InventoryItemsTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.InventoryItemsBO;
import com.example.rms.dto.InventoryItemsdto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.model.InventoryItemsModel;
import com.example.rms.model.PaymentsModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItemsController {

    @FXML
    private Label Employeeid;

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
    private AnchorPane InventoryAPid;

    @FXML
    private Button btngeneratereport;

    @FXML
    private TableColumn<InventoryItemsTm, String> colDescription;

    @FXML
    private TableColumn<InventoryItemsTm, String> colInventoryItemId;

    @FXML
    private TableColumn<InventoryItemsTm, String> colName;

    @FXML
    private TableColumn<InventoryItemsTm, String> colQuantity;

    @FXML
    private TableColumn<InventoryItemsTm, String> colUnit;

    @FXML
    private TableView<InventoryItemsTm> tblInventory;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtInventoryItemId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnit;

    public InventoryItemsBO inventoryItemsBO = (InventoryItemsBO) BOFactory.getInstance().getBO(BOFactory.BOType.INVENTORY_ITEM);


    @FXML
    public void initialize() {
        colInventoryItemId.setCellValueFactory(new PropertyValueFactory<>("inventoryItemId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("inventoryName"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("inventoryDescription"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("inventoryQuantity"));
        colUnit.setCellValueFactory(new PropertyValueFactory<>("inventoryUnit"));

        try {
            txtInventoryItemId.setText(inventoryItemsBO.getNextInventoryItemId());
            refreshPage();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setupEnterKeyListeners();
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        InventoryAPid.getChildren().clear();
        InventoryAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String itemId = txtInventoryItemId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this item?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = inventoryItemsBO.deleteInventoryItem(itemId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Item deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete item!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        // Implement report generation logic here
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        InventoryItemsdto item = new InventoryItemsdto(
                txtInventoryItemId.getText(),
                txtName.getText(),
                txtDescription.getText(),
                txtQuantity.getText(),
                txtUnit.getText()
        );

        boolean isSaved = inventoryItemsBO.saveInventoryItem(item);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Item saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save item!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        InventoryItemsdto item = new InventoryItemsdto(
                txtInventoryItemId.getText(),
                txtName.getText(),
                txtDescription.getText(),
                txtQuantity.getText(),
                txtUnit.getText()
        );

        boolean isUpdated = inventoryItemsBO.updateInventoryItem(item);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Item updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update item!").show();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        InventoryItemsTm selectedItem = tblInventory.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtInventoryItemId.setText(selectedItem.getInventoryItemId());
            txtName.setText(selectedItem.getInventoryName());
            txtDescription.setText(selectedItem.getInventoryDescription());
            txtQuantity.setText(selectedItem.getInventoryQuantity());
            txtUnit.setText(selectedItem.getInventoryUnit());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();
        txtInventoryItemId.setText(inventoryItemsBO.getNextInventoryItemId());
        txtName.clear();
        txtDescription.clear();
        txtQuantity.clear();
        txtUnit.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryItemsdto> items = inventoryItemsBO.getAllInventoryItems();
        ObservableList<InventoryItemsTm> itemTMs = FXCollections.observableArrayList();

        for (InventoryItemsdto item : items) {
            itemTMs.add(new InventoryItemsTm(
                    item.getInventoryItemId(),
                    item.getInventoryName(),
                    item.getInventoryDescription(),
                    item.getInventoryQuantity(),
                    item.getInventoryUnit()
            ));
        }
        tblInventory.setItems(itemTMs);
    }

    @FXML
    private TextField txtSearchInventoryId;

    @FXML
    private Button btnSearchInventory;

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String inventoryItemId = txtSearchInventoryId.getText();

        // Reset border color for the search field
        txtSearchInventoryId.setStyle(txtSearchInventoryId.getStyle() + ";-fx-border-color: #7367F0;");

        if (inventoryItemId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Inventory ID to search!").show();
            txtSearchInventoryId.setStyle(txtSearchInventoryId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        InventoryItemsdto item = InventoryItemsModel.searchInventoryItem(inventoryItemId);

        if (item != null) {
            txtInventoryItemId.setText(item.getInventoryItemId());
            txtName.setText(item.getInventoryName());
            txtDescription.setText(item.getInventoryDescription());
            txtQuantity.setText(item.getInventoryQuantity());
            txtUnit.setText(item.getInventoryUnit());

            // Disable the Save button and enable Update/Delete buttons
            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        } else {
            // Display error if payment is not found
            new Alert(Alert.AlertType.ERROR, "Item not found!").show();
            refreshPage();
        }
    }
    private void setupEnterKeyListeners() {
        txtInventoryItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtName.requestFocus(); // Move focus to the Name field
            }
        });

        txtName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDescription.requestFocus(); // Move focus to the Address field
            }
        });

        txtDescription.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQuantity.requestFocus(); // Move focus to the Phone field
            }
        });

        txtQuantity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtUnit.requestFocus(); // Move focus to the Email field
            }
        });

//        txtEmail.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtUserId.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtUnit.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }


}
