package com.example.rms.controller;

import com.example.rms.Tm.OrderItemsTm;
import com.example.rms.Tm.OrdersTm;
import com.example.rms.db.DBConnection;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.dto.Ordersdto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.model.OrdersModel;
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
import java.util.*;

public class OrdersController implements Initializable {

    @FXML
    private Label Employeeid;

    @FXML
    private AnchorPane OrderApid;

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
    private Button btnPlace;


    @FXML
    private Button btngeneratereport;

    @FXML
    private TableColumn<OrdersTm, String> colOrderId;

    @FXML
    private TableColumn<OrdersTm, Date> colOrderdate;

    @FXML
    private TableColumn<OrdersTm, String> colOrdertype;

    @FXML
    private TableColumn<OrdersTm, String> colPaymentId;

    @FXML
    private TableColumn<OrdersTm, String> colReservationId;

    @FXML
    private TableColumn<OrdersTm, String> colStatus;

    @FXML
    private TableColumn<OrdersTm, Double> colTotalAmount;

    @FXML
    private TableColumn<OrdersTm, String> colcustomerId;

    @FXML
    private TableView<OrdersTm> tblOrders;

    @FXML
    private TextField txtCustomerId;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtOrderdate;

    @FXML
    private TextField txtOrdertype;

    @FXML
    private TextField txtPaymentId;

    @FXML
    private TextField txtReservationId;

    @FXML
    private TextField txtStatus;

    @FXML
    private TextField txtTotalAmount;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colOrderdate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colOrdertype.setCellValueFactory(new PropertyValueFactory<>("orderType"));
        colPaymentId.setCellValueFactory(new PropertyValueFactory<>("paymentId"));
        colReservationId.setCellValueFactory(new PropertyValueFactory<>("reservationId"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("orderStatus"));
        colTotalAmount.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));
        colcustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        try {
            txtOrderId.setText(OrdersModel.getNextOrderId());
            refreshPage();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        showDate();
    }

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDate today = LocalDate.now(); // Get current date
            txtOrderdate.setText(today.format(dateFormatter)); // Set it to the date label
        }));

        dateTimeline.setCycleCount(Animation.INDEFINITE); // Keep running indefinitely
        dateTimeline.play(); // Start the timeline for the date
    }

    @FXML
    void BackOA(ActionEvent event) throws IOException {
        OrderApid.getChildren().clear();
        OrderApid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void DeleteOA(ActionEvent event) throws SQLException {
        String orderId = txtOrderId.getText();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this order?", ButtonType.YES, ButtonType.NO);
        if (alert.showAndWait().get() == ButtonType.YES) {
            boolean isDeleted = OrdersModel.deleteOrder(orderId);
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Order deleted successfully!").show();
                refreshPage();
            } else {
                new Alert(Alert.AlertType.ERROR, "Failed to delete order!").show();
            }
        }
    }

    @FXML
    void GenerateOA(ActionEvent event) {
        Connection connection = null;
        try {
            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/order.jrxml"));
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
        Ordersdto order = new Ordersdto(
                txtOrderId.getText(),
                txtReservationId.getText(),
                txtCustomerId.getText(),
                txtPaymentId.getText(),
                Date.valueOf(txtOrderdate.getText()),
                Double.parseDouble(txtTotalAmount.getText()),
                txtStatus.getText(),
                txtOrdertype.getText()
        );

        boolean isSaved = OrdersModel.saveOrder(order);

        if (isSaved) {
            new Alert(Alert.AlertType.INFORMATION, "Order saved successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to save order!").show();
        }
    }

    @FXML
    void UpdateOA(ActionEvent event) throws SQLException {
        Ordersdto order = new Ordersdto(
                txtOrderId.getText(),
                txtReservationId.getText(),
                txtCustomerId.getText(),
                txtPaymentId.getText(),
                Date.valueOf(txtOrderdate.getText()),
                Double.parseDouble(txtTotalAmount.getText()),
                txtStatus.getText(),
                txtOrdertype.getText()
        );

        boolean isUpdated = OrdersModel.updateOrder(order);

        if (isUpdated) {
            new Alert(Alert.AlertType.INFORMATION, "Order updated successfully!").show();
            refreshPage();
        } else {
            new Alert(Alert.AlertType.ERROR, "Failed to update order!").show();
        }
    }

    @FXML
    void OnClickTable(MouseEvent event) {
        OrdersTm selectedItem = tblOrders.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            txtOrderId.setText(selectedItem.getOrderId());
            txtReservationId.setText(selectedItem.getReservationId());
            txtCustomerId.setText(selectedItem.getCustomerId());
            txtPaymentId.setText(selectedItem.getPaymentId());
            txtOrderdate.setText(selectedItem.getOrderDate().toString());
            txtTotalAmount.setText(String.valueOf(selectedItem.getTotalAmount()));
            txtStatus.setText(selectedItem.getOrderStatus());
            txtOrdertype.setText(selectedItem.getOrderType());

            btnSave.setDisable(true);
            btnDelete.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    private void refreshPage() throws SQLException {
        refreshTable();
        txtOrderId.setText(OrdersModel.getNextOrderId());
        txtReservationId.clear();
        txtCustomerId.clear();
        txtPaymentId.clear();
        txtOrderdate.clear();
        txtTotalAmount.clear();
        txtStatus.clear();
        txtOrdertype.clear();

        btnSave.setDisable(false);
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void refreshTable() throws SQLException {
        ArrayList<Ordersdto> orders = OrdersModel.getAllOrders();
        ObservableList<OrdersTm> orderTMs = FXCollections.observableArrayList();

        for (Ordersdto order : orders) {
            orderTMs.add(new OrdersTm(
                    order.getOrderId(),
                    order.getReservationId(),
                    order.getCustomerId(),
                    order.getPaymentId(),
                    order.getOrderDate(),
                    order.getTotalAmount(),
                    order.getOrderStatus(),
                    order.getOrderType()
            ));
        }
        tblOrders.setItems(orderTMs);
    }

    @FXML
    private TextField txtSearchOrderId;

    @FXML
    private Button btnSearchOrder; // Button to trigger search action

    @FXML
    void searchOnAction(ActionEvent event) throws SQLException {
        String orderId = txtSearchOrderId.getText();

        // Reset border color for the search field
        txtSearchOrderId.setStyle(txtSearchOrderId.getStyle() + ";-fx-border-color: #7367F0;");

        if (orderId.isEmpty()) {
            // Display warning if the search field is empty
            new Alert(Alert.AlertType.WARNING, "Please enter a Order ID to search!").show();
            txtSearchOrderId.setStyle(txtSearchOrderId.getStyle() + ";-fx-border-color: red;");
            return;
        }


        Ordersdto order = OrdersModel.searchOrder(orderId);

        if (order != null) {

            txtReservationId.setText(order.getReservationId());
            txtCustomerId.setText(order.getCustomerId());
            txtPaymentId.setText(order.getPaymentId());
            txtOrderdate.setText(order.getOrderDate().toString());
            txtTotalAmount.setText(String.valueOf(order.getTotalAmount()));
            txtStatus.setText(order.getOrderStatus());
            txtOrdertype.setText(order.getOrderType());


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
