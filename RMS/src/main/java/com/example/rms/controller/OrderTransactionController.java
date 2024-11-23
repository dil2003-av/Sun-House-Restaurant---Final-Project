//package com.example.rms.controller;
//
//import com.ijse.rms.db.DBConnection;
//import com.ijse.rms.dto.*;
//import com.ijse.rms.dto.tm.CartTM;
//import com.ijse.rms.model.*;
//import javafx.animation.KeyFrame;
//import javafx.animation.Timeline;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.control.*;
//import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.util.Duration;
//import net.sf.jasperreports.engine.*;
//import net.sf.jasperreports.view.JasperViewer;
//
//import java.net.URL;
//import java.sql.Connection;
//import java.sql.SQLException;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.ResourceBundle;
//
//public class OrderTransactionController implements Initializable {
//
//    @FXML
//    private Button btnAddMenuItem,btnRemoveMenuItem ,btnDelete, btnPlaceOrder, btnReset, btnSearch, btnSearchMenuItem, btnUpdate, btnCustomerSearch;
//
//    @FXML
//    private ChoiceBox<String> choiceBoxOrderType, choiseBoxOrderStatus, choiseBoxPaymentMethod;
//
//    @FXML
//    private TableView<CartTM> tblMenuItem;
//
//    @FXML
//    private TableColumn<CartTM, String> colMenuItemId, colMenuItemName;
//
//    @FXML
//    private TableColumn<CartTM, Integer> colMenuItemQty;
//
//    @FXML
//    private TableColumn<CartTM, Double> colMenuItemUnitPrice, colMenuItemPrice;
//
//    @FXML
//    private Label lblTotalAmountShow, lblOrderDate;
//
//    @FXML
//    private TextField txtOrdersId, txtCustomerId, txtMenuItemId, txtMenuItemName, txtMenuItemQty, txtMenuItemUnitPrice, txtPaymentId, txtReservationId, txtUserId;
//
//
//    @FXML
//    private Button btnGenarator;
//
//    @FXML
//    private Label lblCustomerId;
//
//    @FXML
//    private Label lblCustomerTele;
//
//    @FXML
//    private Label lblDate;
//
//    @FXML
//    private Label lblMenuItemId;
//
//    @FXML
//    private Label lblMenuItemName;
//
//    @FXML
//    private Label lblMenuItemQty;
//
//    @FXML
//    private Label lblMenuItemUnitPrice;
//
//    @FXML
//    private Label lblOrderAmount;
//
//    @FXML
//    private Label lblOrderStatus;
//
//    @FXML
//    private Label lblOrderType;
//
//    @FXML
//    private Label lblOrdersId;
//
//    @FXML
//    private Label lblPaymentId;
//
//    @FXML
//    private Label lblPaymentId1;
//
//    @FXML
//    private Label lblReservationId;
//
//    @FXML
//    private Label lblTotalAmount;
//
//    @FXML
//    private Label lblUserId;
//
//    @FXML
//    private TextField txtCustomerTele;
//
//    @FXML
//    private TextField txtOrderAmount;
//
//    @FXML
//    private AnchorPane orderSubPane;
//
//    private final OrderViewModel orderModel = new OrderViewModel();
//    private final OrderTransactionModel orderTransactionModel = new OrderTransactionModel();
//    private final ObservableList<CartTM> cartTMS = FXCollections.observableArrayList();
//
//    static double totalAmount = 0;
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        btnDelete.setVisible(false);
//        showDate();
//        initializeOrderDetails();
//        setCellValues();
//    }
//
//    private void initializeOrderDetails() {
//        try {
//            txtOrdersId.setText(getNextOrderId());
//            txtPaymentId.setText(PaymentModel.getNextPaymentID());
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error initializing order details: " + e.getMessage());
//        }
//    }
//
//    private void setCellValues() {
//        colMenuItemId.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
//        colMenuItemName.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
//        colMenuItemUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
//        colMenuItemQty.setCellValueFactory(new PropertyValueFactory<>("addToCartMenuItemQty"));
//        colMenuItemPrice.setCellValueFactory(new PropertyValueFactory<>("amount"));
//        tblMenuItem.setItems(cartTMS);
//    }
//
//    private String getNextOrderId() {
//        try {
//            return orderModel.getNextOrderId();
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error fetching next Order ID: " + e.getMessage());
//            return "";
//        }
//    }
//
//    @FXML
//    void btnAddMenuItemOnAction(ActionEvent event) { //add to cart
//
//        try {
//            String menuItemId = txtMenuItemId.getText();
//            String menuItemName = txtMenuItemName.getText();
//            int qty = Integer.parseInt(txtMenuItemQty.getText());
//            double unitPrice = Double.parseDouble(txtMenuItemUnitPrice.getText());
//
//            double total = qty * unitPrice;
//
//            for (CartTM cartItem : cartTMS) {
//                if (cartItem.getMenuItemId().equals(menuItemId)) {
//                    int newQty = cartItem.getAddToCartMenuItemQty() + qty;
//                    cartItem.setAddToCartMenuItemQty(newQty);
//                    cartItem.setAmount(newQty * unitPrice);
//
//                    totalAmount += (newQty * unitPrice);
//
//                    tblMenuItem.refresh();
//                    return;
//                }
//            }
//
//            CartTM newCartItem = new CartTM(menuItemId, menuItemName, unitPrice, qty, total,qty*unitPrice,totalAmount);
//            cartTMS.add(newCartItem);
//            tblMenuItem.setItems(cartTMS);
//        } catch (NumberFormatException e) {
//            showAlert(Alert.AlertType.ERROR, "Invalid quantity or price format.");
//        }
//
//        calculateOrderAmount();
//    }
//    @FXML
//    void btnRemoveMenuItemOnAction(ActionEvent event) { //remove to cart
//        // Get the selected item from the table
//        CartTM selectedCartItem = tblMenuItem.getSelectionModel().getSelectedItem();
//
//        if (selectedCartItem != null) {
//            // Deduct the amount of the selected item from the total amount
//            totalAmount -= selectedCartItem.getAmount();
//
//            // Remove the item from the cart list
//            cartTMS.remove(selectedCartItem);
//
//            // Refresh the table view
//            tblMenuItem.refresh();
//
//            // Recalculate the order amount
//            calculateOrderAmount();
//        } else {
//            // Show an alert if no item is selected
//            showAlert(Alert.AlertType.WARNING, "Please select an item to remove.");
//        }
//
//    }
//
//
////    @FXML
////    void btnPlaceOrderOnAction(ActionEvent event) {
////        try {
////            String orderId = txtOrdersId.getText();
////            String customerId = txtCustomerId.getText();
////            String userId = txtUserId.getText();
////            LocalDate orderDate = LocalDate.now();
////            double totalAmount = cartTMS.stream().mapToDouble(CartTM::getAmount).sum();
////            String orderStatus = choiseBoxOrderStatus.getValue();
////            String orderType = choiceBoxOrderType.getValue();
////            String paymentId = txtPaymentId.getText();
////            String resavationId = txtReservationId.getText();
////
////            OrderViewDto order = new OrderViewDto(orderId, customerId, userId, orderDate, totalAmount, orderStatus, orderType, resavationId, paymentId);
////
////            boolean isSaved = orderTransactionModel.placeOrder(order,orderItemDtos,paymentDtos);
////            if (isSaved) {
////                showAlert(Alert.AlertType.INFORMATION, "Order placed successfully!");
////                resetPage();
////            } else {
////                showAlert(Alert.AlertType.ERROR, "Failed to place order.");
////            }
////        } catch (SQLException e) {
////            showAlert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage());
////        }
////    }
//
//    @FXML
//    void btnPlaceOrderOnAction(ActionEvent event) {
//        try {
//            // Validate user inputs
//            if (txtOrdersId.getText().isEmpty() || txtCustomerId.getText().isEmpty() ||
//                    txtUserId.getText().isEmpty() || txtPaymentId.getText().isEmpty() ||
//                    choiceBoxOrderType.getValue() == null || choiseBoxOrderStatus.getValue() == null) {
//                showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
//                return;
//            }
//
//            // Extract inputs
//            String orderId = txtOrdersId.getText();
//            String customerId = txtCustomerId.getText();
//            String userId = txtUserId.getText();
//            LocalDate orderDate = LocalDate.now();
//            double totalAmount = cartTMS.stream().mapToDouble(CartTM::getAmount).sum();
//            String orderStatus = choiseBoxOrderStatus.getValue();
//            String orderType = choiceBoxOrderType.getValue();
//            String paymentId = txtPaymentId.getText();
//            String reservationId = txtReservationId.getText().isEmpty() ? null : txtReservationId.getText(); // Allow null if not provided
//
//            // Create Order DTO
//            OrderViewDto order = new OrderViewDto(orderId, customerId, userId, orderDate, totalAmount, orderStatus, orderType, reservationId, paymentId);
//
//            // Create OrderItemDtos from cartTMS
//            ArrayList<OrderItemDto> orderItemDtos = new ArrayList<>();
//            for (CartTM cartItem : cartTMS) {
//                OrderItemDto orderItem = new OrderItemDto(
//                        null, // Assume OrderItemID is auto-generated
//                        orderId,
//                        cartItem.getMenuItemId(),
//                        cartItem.getAddToCartMenuItemQty(),
//                        cartItem.getAmount()
//                );
//                orderItemDtos.add(orderItem);
//            }
//
//            // Create PaymentDtos
//            ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
//            paymentDtos.add(new PaymentDto(paymentId, choiseBoxPaymentMethod.getValue(), totalAmount, orderDate));
//
//            // Place the order
//            boolean isSaved = orderTransactionModel.placeOrder(order, orderItemDtos, paymentDtos);
//            if (isSaved) {
//                showAlert(Alert.AlertType.INFORMATION, "Order placed successfully!");
//                resetPage(); // Reset page after success
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Failed to place order. Please try again.");
//            }
//        } catch (SQLException e) {
//            showAlert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage());
//        }
////
////        catch (Exception e) {
////            showAlert(Alert.AlertType.ERROR, "Unexpected error: " + e.getMessage());
////
////        }
//    }
//
//    @FXML
//    void btnSearchOnAction(ActionEvent event) {
//        String orderId = txtOrdersId.getText(); // Assuming orderId is entered in txtOrdersId
//
//        try {
//            OrderViewDto order = orderModel.orderSearch(orderId);
//
//            if (order != null) {
//                // Populate the text fields with order details
//                txtCustomerId.setText(order.getCustomerId());
//                choiceBoxOrderType.setValue(order.getOrderType());
//                txtPaymentId.setText(order.getPaymentId());
//                txtReservationId.setText(order.getReservationId());
//                choiseBoxOrderStatus.setValue(order.getOrderStatus());
//                txtUserId.setText(order.getUserId());
//                txtOrderAmount.setText(String.valueOf(order.getTotalAmount()));
//                lblOrderDate.setText(order.getOrderDate().toString());
//
////                btnUpdate.setDisable(false);
////                btnDelete.setDisable(false);
////                btnSave.setDisable(true);
//            } else {
//                // Create an alert of type INFORMATION or WARNING
//                Alert alert = new Alert(Alert.AlertType.WARNING);
//                alert.setTitle("Order Not Found");
//                alert.setHeaderText(null); // Optional: set this to null if no header is needed
//                alert.setContentText("The order you are looking for was not found.");
//
//                // Show the alert and wait for user action
//                alert.showAndWait();
//               // new Alert(Alert.AlertType.WARNING, "Order Not Found!", ButtonType.OK).showAndWait();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void btnSearchMenuItemOnAction(ActionEvent event) {
//        try {
//            String MenuItemName = txtMenuItemName.getText();
//            MenuItemDto menuItemDto = MenuItemModel.searchMenuItemName(MenuItemName);
//            if (menuItemDto != null) {
//                txtMenuItemName.setText(menuItemDto.getName());
//                txtMenuItemId.setText(menuItemDto.getId());
//                txtMenuItemUnitPrice.setText(String.valueOf(menuItemDto.getPrice()));
//
//            } else {
//                new Alert(Alert.AlertType.WARNING, "Item not found!", ButtonType.OK).showAndWait();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void resetPage() {
//        cartTMS.clear();
//        txtOrdersId.setText(getNextOrderId());
//        txtCustomerId.clear();
//        txtMenuItemId.clear();
//        txtMenuItemName.clear();
//        txtMenuItemQty.clear();
//        txtMenuItemUnitPrice.clear();
//        txtPaymentId.clear();
//        choiceBoxOrderType.setValue(null);
//        choiseBoxOrderStatus.setValue(null);
//        lblTotalAmountShow.setText("0.00");
//        tblMenuItem.refresh();
//    }
//
//    private void showDate() {
//        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
//            lblOrderDate.setText(LocalDate.now().format(dateFormatter));
//        }));
//        dateTimeline.setCycleCount(Timeline.INDEFINITE);
//        dateTimeline.play();
//    }
//
//    private void showAlert(Alert.AlertType alertType, String message) {
//        Alert alert = new Alert(alertType);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//
///////////////////////////////////
//
//    @FXML
//    void btnCustomerSearchOnAction(ActionEvent event) {
//        //tele eken set wenna ona customer ----> cus id eka
//        try {
//            String customerMobile = txtCustomerTele.getText();
//            CustomerViewDto customerDto = CustomerViewModel.searchCustomerMobile(customerMobile);
//            if (customerDto != null) {
//                txtCustomerTele.setText(customerDto.getPhone());
//                txtCustomerId.setText(customerDto.getId());
//
//            } else {
//                new Alert(Alert.AlertType.WARNING, "Customer not found!", ButtonType.OK).showAndWait();
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void btnGACOnAction(ActionEvent event) {
//        Connection connection = null;
//        try {
//            JasperReport jasperReport = JasperCompileManager.compileReport(getClass().getResourceAsStream("/report/Order.jrxml"));
//            connection = DBConnection.getInstance().getConnection();
//            //<key/index,value> eka tamai string object widiyata danne
//            Map<String,Object> parameters = new HashMap<>();
//            parameters.put("orderid", txtOrdersId.getText());
//            //map ekka wada karana kota duplicate karanne naaa
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
//            JasperViewer.viewReport(jasperPrint); // Viewing the report
//        } catch (JRException | SQLException e) {
//            e.printStackTrace(); // Print the exception stack trace
////        } finally {
////            if (connection != null) {
////                try {
////                    connection.close(); // Ensure the connection is closed
////                } catch (SQLException e) {
////                    e.printStackTrace(); // Handle connection closing exception
////                }
////            }
//        }
//    }
//
//    @FXML
//    void btnDeleteOnAction(ActionEvent event) {
//        //complsary na
//    }
//
//
//
//    @FXML
//    void btnResetOnAction(ActionEvent event) throws SQLException {
//        cartTMS.clear();
//        tblMenuItem.refresh();
//        txtOrdersId.setText(getNextOrderId());
//        txtCustomerId.clear();
//        txtCustomerTele.clear();
//        txtMenuItemId.clear();
//        txtMenuItemName.clear();
//        txtMenuItemQty.clear();
//        txtMenuItemUnitPrice.clear();
//        txtPaymentId.setText(PaymentModel.getNextPaymentID());
//        txtReservationId.clear();
//        choiceBoxOrderType.setValue(null);
//        choiseBoxOrderStatus.setValue(null);
//        lblTotalAmountShow.setText("0.00");
//        totalAmount = 0; // Reset total amount
//    }
//
//    @FXML
//    void btnUpdateOnAction(ActionEvent event) {
//        //only order status eka witar update wenne
//        try {
//            String orderId = txtOrdersId.getText(); // Assume the order ID is filled
//            String status = choiseBoxOrderStatus.getValue(); // Get the selected status
//
//            if (orderId.isEmpty() || status == null) {
//                showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
//                return;
//            }
//
//            // Update order status using the model method
//            boolean isUpdated = orderTransactionModel.updateOrderStatus(orderId, status);
//            if (isUpdated) {
//                showAlert(Alert.AlertType.INFORMATION, "Order status updated successfully!");
//            } else {
//                showAlert(Alert.AlertType.ERROR, "Failed to update the order status.");
//            }
//        } catch (Exception e) {
//            showAlert(Alert.AlertType.ERROR, "Error updating order status: " + e.getMessage());
//        }
//    }
//
//    @FXML
//    void onClickTable(MouseEvent event) {
//        // Get the selected item from the table
//        CartTM selectedItem = tblMenuItem.getSelectionModel().getSelectedItem();
//
//        if (selectedItem != null) {
//            // Populate the text fields with details from the selected item
//            txtMenuItemId.setText(selectedItem.getMenuItemId());
//            txtMenuItemName.setText(selectedItem.getMenuItemName());
//            txtMenuItemQty.setText(String.valueOf(selectedItem.getAddToCartMenuItemQty()));
//            txtMenuItemUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
//        }
//    }
//
//    public void calculateOrderAmount() {
//        int qty = 0;
//        double amount = 0;
//
//        for(CartTM cartTM : cartTMS) {
//            double newQty = cartTM.getAddToCartMenuItemQty() + qty ;
//            double unitPrice = cartTM.getUnitPrice();
//            amount += newQty * unitPrice;
//
//        }
//        lblTotalAmountShow.setText(String.valueOf(amount));
//    }
//
//}