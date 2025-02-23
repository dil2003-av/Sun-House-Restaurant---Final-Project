package com.example.rms.controller;

import com.example.rms.Tm.OrderItemsTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.InventoryItemsBO;
import com.example.rms.bo.custom.OrderItemBO;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.dto.Ordersdto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.model.OrderItemsModel;
import com.example.rms.model.PaymentsModel;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class OrderItemsController implements Initializable {

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane OrderItemsAPid;

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
    private TableColumn<OrderItemsTm, String> colOrderItemId;

    @FXML
    private TableColumn<OrderItemsTm, String> colOrderId;

    @FXML
    private TableColumn<OrderItemsTm, String> colMenuItemId;

    @FXML
    private TableColumn<OrderItemsTm, String> colQuantity;

    @FXML
    private TableColumn<OrderItemsTm, Double> colPrice;

    @FXML
    private TableView<OrderItemsTm> tblOrderItems;

    @FXML
    private TextField txtOrderItemId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtMenuItemId;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtPrice;

    public OrderItemBO orderItemBO = (OrderItemBO) BOFactory.getInstance().getBO(BOFactory.BOType.ORDER_ITEM);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderItemId.setCellValueFactory(new PropertyValueFactory<>("orderItemId"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colMenuItemId.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        try {
            txtOrderItemId.setText(orderItemBO.getNextOrderItemId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setupEnterKeyListeners();
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        OrderItemsAPid.getChildren().clear();
        OrderItemsAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        String orderItemId = txtOrderItemId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order item?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = orderItemBO.deleteOrderItem(orderItemId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Order item deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete order item!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        // Code to generate report can be added here.
    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        refreshPage();
    }

    @FXML
    void SaveOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        OrderItemsdto orderItem = new OrderItemsdto(
                txtOrderItemId.getText(),
                txtOrderId.getText(),
                txtMenuItemId.getText(),
                txtQuantity.getText(),
                Double.parseDouble(txtPrice.getText())
        );

        boolean isSaved = orderItemBO.saveOrderItem(orderItem);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order item saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save order item!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException, ClassNotFoundException {
        OrderItemsdto orderItem = new OrderItemsdto(
                txtOrderItemId.getText(),
                txtOrderId.getText(),
                txtMenuItemId.getText(),
                txtQuantity.getText(),
                Double.parseDouble(txtPrice.getText())
        );

        boolean isUpdated = orderItemBO.updateOrderItem(orderItem);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Order item updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update order item!").show();
        }
    }

    @FXML
    void onMouseClicked(MouseEvent event) {
        OrderItemsTm selectedItem = tblOrderItems.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtOrderItemId.setText(selectedItem.getOrderItemId());
            txtOrderId.setText(selectedItem.getOrderId());
            txtMenuItemId.setText(selectedItem.getMenuItemId());
            txtQuantity.setText(selectedItem.getQuantity());
            txtPrice.setText(String.valueOf(selectedItem.getPrice()));

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException, ClassNotFoundException {
        refreshTable();
        txtOrderItemId.setText(orderItemBO.getNextOrderItemId());
        txtOrderId.clear();
        txtMenuItemId.clear();
        txtQuantity.clear();
        txtPrice.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException, ClassNotFoundException {
        ArrayList<OrderItemsdto> orderItems = orderItemBO.getAllOrderItems();
        ObservableList<OrderItemsTm> orderItemsTMs = FXCollections.observableArrayList();

        for (OrderItemsdto orderItem : orderItems) {
            orderItemsTMs.add(new OrderItemsTm(
                    orderItem.getOrderItemId(),
                    orderItem.getOrderId(),
                    orderItem.getMenuItemId(),
                    orderItem.getQuantity(),
                    orderItem.getPrice()
            ));
        }
        tblOrderItems.setItems(orderItemsTMs);
    }
    @FXML
    void placeOA(ActionEvent event) throws SQLException {
        // Create the Ordersdto object using form data
        OrderItemsdto orderItemsdto = new OrderItemsdto(
                txtOrderItemId.getText(),
                txtOrderId.getText(),
                txtMenuItemId.getText(),
                txtQuantity.getText(),
                Double.parseDouble(txtPrice.getText())

        );

        // Create the list of OrderItemsdto from the table
        ArrayList<OrderItemsdto> orderItems = new ArrayList<>();
        for (OrderItemsTm item : tblOrderItems.getItems()) {
            orderItems.add(new OrderItemsdto(
                    item.getOrderItemId(),
                    item.getOrderId(),
                    item.getMenuItemId(),
                    item.getQuantity(),
                    item.getPrice()
            ));
        }

}
    @FXML
    private TextField txtSearchOrderItemsId;

    @FXML
    private Button btnSearchOrderItem; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException, ClassNotFoundException {
        String orderItemId = txtSearchOrderItemsId.getText();

        // Reset border color for the search field
        txtSearchOrderItemsId.setStyle(txtSearchOrderItemsId.getStyle() + ";-fx-border-color: #7367F0;");

        if (orderItemId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a OrderItem ID to search!").show();
            txtSearchOrderItemsId.setStyle(txtSearchOrderItemsId.getStyle() + ";-fx-border-color: red;");
            return;
        }


        OrderItemsdto orderItem = OrderItemsModel.searchOrderItem(orderItemId);

        if (orderItem != null) {

            txtOrderItemId.setText(orderItem.getOrderItemId());
            txtOrderId.setText(orderItem.getOrderId());
            txtMenuItemId.setText(orderItem.getMenuItemId());
            txtQuantity.setText(orderItem.getQuantity());
            txtPrice.setText(String.valueOf(orderItem.getPrice()));

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
        txtOrderItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtOrderId.requestFocus(); // Move focus to the Name field
            }
        });

        txtOrderId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtMenuItemId.requestFocus(); // Move focus to the Address field
            }
        });

        txtMenuItemId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQuantity.requestFocus(); // Move focus to the Phone field
            }
        });

        txtQuantity.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPrice.requestFocus(); // Move focus to the Email field
            }
        });

//        txtCategory.setOnKeyPressed(event -> {
//            if (event.getCode() == KeyCode.ENTER) {
//                txtKitchenSection.requestFocus(); // Move focus to the User ID field
//            }
//        });

        txtPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                btnSave.fire(); // Trigger Save button's action
            }
        });
    }



}
