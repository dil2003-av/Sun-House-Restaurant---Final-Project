package com.example.rms.controller;

import com.example.rms.Tm.DeliveriesTm;
import com.example.rms.dto.Deliveriesdto;
import com.example.rms.model.DeliveriesModel;
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

public class DeliveriesController implements Initializable {

    @FXML
    private Button Deleteid;

    @FXML
    private AnchorPane DeliveryAPid;

    @FXML
    private Button Saveid;

    @FXML
    private Button btnBack;

    @FXML
    private TableColumn<DeliveriesTm, String> colDeliverAddress;

    @FXML
    private TableColumn<DeliveriesTm, String> colDeliverID;

    @FXML
    private TableColumn<DeliveriesTm, Date> colDeliveryDate;

    @FXML
    private TableColumn<DeliveriesTm, String> colDeliveryStatus;

    @FXML
    private TableColumn<DeliveriesTm, String> colOrderID;

    @FXML
    private Button generatereportid;

    @FXML
    private Button resetid;

    @FXML
    private TableView<DeliveriesTm> tblDelivery;

    @FXML
    private TextField txtDeliverID;

    @FXML
    private TextField txtDeliveryAddress;

    @FXML
    private TextField txtDeliveryDate;

    @FXML
    private TextField txtDeliveryStatus;

    @FXML
    private TextField txtOrderID;

    @FXML
    private Button updateid;

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtDeliveryDate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colDeliverID.setCellValueFactory(new PropertyValueFactory<>("deliveryId"));
        colOrderID.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colDeliveryDate.setCellValueFactory(new PropertyValueFactory<>("deliveryDate"));
        colDeliveryStatus.setCellValueFactory(new PropertyValueFactory<>("deliveryStatus"));
        colDeliverAddress.setCellValueFactory(new PropertyValueFactory<>("deliveryAddress"));

        try {
            txtDeliverID.setText(DeliveriesModel.getNextDeliveryId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showDate();
        setupEnterKeyListeners();
    }

    private void setupEnterKeyListeners() {
        txtDeliverID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtOrderID.requestFocus(); // Move focus to the Order ID field
            }
        });

        txtOrderID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDeliveryDate.requestFocus(); // Move focus to the Delivery Date field
            }
        });

        txtDeliveryDate.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDeliveryStatus.requestFocus(); // Move focus to the Delivery Status field
            }
        });

        txtDeliveryStatus.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtDeliveryAddress.requestFocus(); // Move focus to the Delivery Address field
            }
        });

        txtDeliveryAddress.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                Saveid.fire(); // Trigger Save button's action
            }
        });
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        DeliveryAPid.getChildren().clear();
        DeliveryAPid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }


    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String deliveryId = txtDeliverID.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this delivery?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = DeliveriesModel.deleteDelivery(deliveryId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete delivery!").show();
            }
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        DeliveriesTm selectedItem = tblDelivery.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtDeliverID.setText(selectedItem.getDeliveryId());
            txtOrderID.setText(selectedItem.getOrderId());
            txtDeliveryDate.setText(selectedItem.getDeliveryDate().toString());
            txtDeliveryStatus.setText(selectedItem.getDeliveryStatus());
            txtDeliveryAddress.setText(selectedItem.getDeliveryAddress());

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
        String deliveryId = txtDeliverID.getText();
        String orderId = txtOrderID.getText();
        String deliveryDate = txtDeliveryDate.getText();
        String deliveryStatus = txtDeliveryStatus.getText();
        String deliveryAddress = txtDeliveryAddress.getText();

        // Define patterns for validation
        String orderIdPattern = "^[A-Za-z0-9]+$"; // Order ID: alphanumeric
        String deliveryStatusPattern = "^(Pending|Delivered|Cancelled)$"; // Status: specific allowed values
        String addressPattern = "^.{1,255}$"; // Address: any characters, up to 255

        // Validate the inputs
        boolean isValidOrderId = orderId.matches(orderIdPattern);
        boolean isValidDeliveryDate;
        try {
            Date.valueOf(deliveryDate); // Validate date format
            isValidDeliveryDate = true;
        } catch (IllegalArgumentException e) {
            isValidDeliveryDate = false;
        }
        boolean isValidStatus = deliveryStatus.matches(deliveryStatusPattern);
        boolean isValidAddress = deliveryAddress.matches(addressPattern);

        // Reset styles
        txtOrderID.setStyle(txtOrderID.getStyle() + "-fx-border-color: #7367F0;");
        txtDeliveryDate.setStyle(txtDeliveryDate.getStyle() + "-fx-border-color: #7367F0;");
        txtDeliveryStatus.setStyle(txtDeliveryStatus.getStyle() + "-fx-border-color: #7367F0;");
        txtDeliveryAddress.setStyle(txtDeliveryAddress.getStyle() + "-fx-border-color: #7367F0;");

        // Highlight invalid fields
        if (!isValidOrderId) {
            txtOrderID.setStyle(txtOrderID.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidDeliveryDate) {
            txtDeliveryDate.setStyle(txtDeliveryDate.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidStatus) {
            txtDeliveryStatus.setStyle(txtDeliveryStatus.getStyle() + "-fx-border-color: red;");
        }
        if (!isValidAddress) {
            txtDeliveryAddress.setStyle(txtDeliveryAddress.getStyle() + "-fx-border-color: red;");
        }


        // If all fields are valid, proceed to save the delivery
        if (isValidOrderId && isValidDeliveryDate && isValidStatus && isValidAddress) {
            Deliveriesdto delivery = new Deliveriesdto(
                    deliveryId,
                    orderId,
                    Date.valueOf(deliveryDate),
                    deliveryStatus,
                    deliveryAddress
            );

            boolean isSaved = DeliveriesModel.saveDelivery(delivery);

            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "Delivery saved successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to save delivery!").show();
            }
        } else {
            new Alert(Alert.AlertType.ERROR, "Invalid input detected. Please correct the highlighted fields.").show();
        }
    }


    @FXML
    void generatereportOA(ActionEvent event) {
        // Implement report generation logic here if needed
    }

    @FXML
    void updateOA(ActionEvent event) throws SQLException {
        Deliveriesdto delivery = new Deliveriesdto(
                txtDeliverID.getText(),
                txtOrderID.getText(),
                Date.valueOf(txtDeliveryDate.getText()),
                txtDeliveryStatus.getText(),
                txtDeliveryAddress.getText()
        );

        boolean isUpdated = DeliveriesModel.updateDelivery(delivery);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Delivery updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update delivery!").show();
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtDeliverID.setText(DeliveriesModel.getNextDeliveryId());
        txtOrderID.clear();
        txtDeliveryDate.clear();
        txtDeliveryStatus.clear();
        txtDeliveryAddress.clear();

        Saveid.setDisable(false);
        Deleteid.setDisable(true);
        updateid.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Deliveriesdto> deliveries = DeliveriesModel.getAllDeliveries();
        ObservableList<DeliveriesTm> deliveryTMs = FXCollections.observableArrayList();

        for (Deliveriesdto delivery : deliveries) {
            deliveryTMs.add(new DeliveriesTm(
                    delivery.getDeliveryId(),
                    delivery.getOrderId(),
                    delivery.getDeliveryDate(),
                    delivery.getDeliveryStatus(),
                    delivery.getDeliveryAddress()
            ));
        }
        tblDelivery.setItems(deliveryTMs);
    }

    @FXML
    private TextField txtSearchDeliveryId; // TextField for searching Delivery by Delivery ID

    @FXML
    private Button btnSearchDelivery; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String deliveryId = txtSearchDeliveryId.getText(); // Get the Delivery ID entered in the search field

        // Reset border color for the search field
        txtSearchDeliveryId.setStyle(txtSearchDeliveryId.getStyle() + ";-fx-border-color: #7367F0;");

        if (deliveryId.isEmpty()) {
            // Display warning if search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Delivery ID to search!").show();
            txtSearchDeliveryId.setStyle(txtSearchDeliveryId.getStyle() + ";-fx-border-color: red;");
            return;
        }

        // Search for the delivery using DeliveriesModel
        Deliveriesdto delivery = DeliveriesModel.searchDelivery(deliveryId);

        if (delivery != null) {
            // Populate the fields with delivery details
            txtDeliverID.setText(delivery.getDeliveryId());
            txtOrderID.setText(delivery.getOrderId());
            txtDeliveryDate.setText(delivery.getDeliveryDate().toString());
            txtDeliveryStatus.setText(delivery.getDeliveryStatus());
            txtDeliveryAddress.setText(delivery.getDeliveryAddress());

            // Disable Save button and enable Update/Delete buttons
            Saveid.setDisable(true);
            Deleteid.setDisable(false);
            updateid.setDisable(false);
        } else {
            // Display error if delivery is not found
            new Alert(Alert.AlertType.WARNING, "No delivery found with ID: " + deliveryId).show();
            refreshPage();
        }
    }



}
