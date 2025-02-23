package com.example.rms.controller;

import com.example.rms.Tm.CartTm;
import com.example.rms.bo.BOFactory;
import com.example.rms.bo.custom.PlaceOrderBO;
import com.example.rms.bo.custom.SuppliersBO;
import com.example.rms.dto.*;
import com.example.rms.model.*;
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

//import static com.example.rms.model.OrderFormModel.getNextOrderId;

public class OrderFormController implements Initializable {

    @FXML
    private Label Employeeid;

    @FXML
    private Label Employeeid1;

    @FXML
    private AnchorPane OrderFormid;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnPay;

    @FXML
    private Button btnPlaceOrder;

    @FXML
    private Button btnRemoveCart;

    @FXML
    private Button btnReset;

    @FXML
    private Button btnUpdate;

    @FXML
    private ChoiceBox<String> choiceBoxOrderType, choiseBoxOrderStatus, choiseBoxPaymentMethod;


    @FXML
    private TableColumn<CartTm, String> colIitemName;

    @FXML
    private TableColumn<CartTm, String> colMenuItemD;

    @FXML
    private TableColumn<CartTm, Double> colPrice;

    @FXML
    private TableColumn<CartTm, Integer> colQty;

    @FXML
    private TableColumn<CartTm, Double> colUnitPrice;

    @FXML
    private Button search;

    @FXML
    private Button searchName;

    @FXML
    private Button searchTele;

//    @FXML
//    private ComboBox<String> cmbOrderType;
//
//    @FXML
//    private ComboBox<String> cmbStatus;

    @FXML
    private TableView<CartTm> tblOrderForm;

    @FXML
    private Label txtAmount;
    @FXML
    private TextField txtCustomerID;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtMenuID;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtReservationID;

    @FXML
    private Label lblOrderDate;

    @FXML
    private TextField txtPaymentID;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearchOrder;

    @FXML
    private TextField txtUnitPrice;

    @FXML
    private TextField txtUserID;

    @FXML
    private TextField txtphone;

    private final OrdersModel ordersModel = new OrdersModel();
    //private final OrderFormModel orderFormModel = new OrderFormModel();
    private final ObservableList<CartTm> cartTMS = FXCollections.observableArrayList();

    public PlaceOrderBO placeOrderBO = (PlaceOrderBO) BOFactory.getInstance().getBO(BOFactory.BOType.PLACE_ORDER);


    static double totalAmount = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //  btnDelete.setVisible(false);
        showDate();
        try {
            initializeOrderDetails();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        setCellValues();
        setupEnterKeyListeners();
    }

    private void showDate() {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Timeline dateTimeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            lblOrderDate.setText(LocalDate.now().format(dateFormatter));
        }));
        dateTimeline.setCycleCount(Timeline.INDEFINITE);
        dateTimeline.play();
    }

    private void initializeOrderDetails() throws SQLException, ClassNotFoundException {
        txtOrderId.setText(getNextOrderId());
        txtPaymentID.setText(placeOrderBO.getNextPaymentId());
    }

    private void setupEnterKeyListeners() {
        txtOrderId.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtReservationID.requestFocus(); // Move focus to the Order ID field
            }
        });

        txtReservationID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtUnitPrice.requestFocus(); // Move focus to the Delivery Date field
            }
        });

        txtUnitPrice.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtItemName.requestFocus(); // Move focus to the Delivery Status field
            }
        });

        txtItemName.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtphone.requestFocus(); // Move focus to the Delivery Address field
            }
        });

        txtphone.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtCustomerID.requestFocus(); // Move focus to the Delivery Date field
            }
        });

        txtCustomerID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtPaymentID.requestFocus(); // Move focus to the Delivery Status field
            }
        });

        txtPaymentID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtMenuID.requestFocus(); // Move focus to the Delivery Address field
            }
        });

        txtMenuID.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQty.requestFocus();
            }
        });
        txtQty.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                txtQty.requestFocus();
            }
        });
    }



    private void setCellValues() {
        colMenuItemD.setCellValueFactory(new PropertyValueFactory<>("menuItemId"));
        colIitemName.setCellValueFactory(new PropertyValueFactory<>("menuItemName"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("addToCartMenuItemQty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("amount"));
        tblOrderForm.setItems(cartTMS);
    }

    private String getNextOrderId() {
        try {
            return placeOrderBO.getNextOrderId();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error fetching next Order ID: " + e.getMessage());
            return "";
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void AddtoCartOA(ActionEvent event) {
        try {
            String menuItemId = txtMenuID.getText();
            String menuItemName = txtItemName.getText();
            int qty = Integer.parseInt(txtQty.getText());
            double unitPrice = Double.parseDouble(txtUnitPrice.getText());

            double total = qty * unitPrice;

            for (CartTm cartItem : cartTMS) {
                if (cartItem.getMenuItemId().equals(menuItemId)) {
                    int newQty = cartItem.getAddToCartMenuItemQty() + qty;
                    cartItem.setAddToCartMenuItemQty(newQty);
                    cartItem.setAmount(newQty * unitPrice);

                    totalAmount += (newQty * unitPrice);

                    tblOrderForm.refresh();
                    return;
                }
            }

            CartTm newCartItem = new CartTm(menuItemId, menuItemName, unitPrice, qty, total,qty*unitPrice,totalAmount);
            cartTMS.add(newCartItem);
            tblOrderForm.setItems(cartTMS);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Invalid quantity or price format.");
        }

        calculateOrderAmount();
    }



    @FXML
    void PayOA(ActionEvent event) {

    }

    @FXML
    void PlaceOrderOA(ActionEvent event) {
        try {
            // Validate user inputs
            if (txtOrderId.getText().isEmpty() || txtCustomerID.getText().isEmpty() ||
                    txtPaymentID.getText().isEmpty() ||
                    choiceBoxOrderType.getValue() == null || choiseBoxOrderStatus.getValue() == null) {
                showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
                return;
            }

            // Extract inputs
            String orderId = txtOrderId.getText();
            String reservationId = txtReservationID.getText().isEmpty() ? null : txtReservationID.getText(); // Allow null if not provided
            String customerId = txtCustomerID.getText();
            String paymentId = txtPaymentID.getText();
            //String userId = txtUserId.getText();
            LocalDate orderDate = LocalDate.now();
            double totalAmount = cartTMS.stream().mapToDouble(CartTm::getAmount).sum();
            String orderStatus = choiseBoxOrderStatus.getValue();
            String orderType = choiceBoxOrderType.getValue();
            // String paymentMethod = choiseBoxPaymentMethod.getValue();


            // Create Order DTO
            Ordersdto order = new Ordersdto(orderId,reservationId, customerId, paymentId, orderDate, totalAmount, orderStatus, orderType);

            // Create OrderItemDtos from cartTMS
            ArrayList<OrderItemsdto> orderItemDtos = new ArrayList<>();
            for (CartTm cartItem : cartTMS) {
                OrderItemsdto orderItem = new OrderItemsdto(
                        null, // Assume OrderItemID is auto-generated
                        orderId,
                        cartItem.getMenuItemId(),
                        cartItem.getAddToCartMenuItemQty(),
                        cartItem.getAmount()
                );
                orderItemDtos.add(orderItem);
            }

            // Create PaymentDtos
            ArrayList<Paymentsdto> paymentDtos = new ArrayList<>();
            paymentDtos.add(new Paymentsdto(paymentId, choiseBoxPaymentMethod.getValue(), totalAmount, Date.valueOf(orderDate)));

            // Place the order
            boolean isSaved = placeOrderBO.placeOrder(order, orderItemDtos, paymentDtos);
            if (isSaved) {
                showAlert(Alert.AlertType.INFORMATION, "Order placed successfully!");
                resetPage(); // Reset page after success
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to place order. Please try again.");
            }
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Error placing order: " + e.getMessage());
        }
    }

    @FXML
    void RemoveCartOA(ActionEvent event) {
        CartTm selectedCartItem = tblOrderForm.getSelectionModel().getSelectedItem();

        if (selectedCartItem != null) {
            // Deduct the amount of the selected item from the total amount
            totalAmount -= selectedCartItem.getAmount();

            // Remove the item from the cart list
            cartTMS.remove(selectedCartItem);

            // Refresh the table view
            tblOrderForm.refresh();

            // Recalculate the order amount
            calculateOrderAmount();
        } else {
            // Show an alert if no item is selected
            showAlert(Alert.AlertType.WARNING, "Please select an item to remove.");
        }

    }

    @FXML
    void ResetOA(ActionEvent event) throws SQLException {
        cartTMS.clear();
        tblOrderForm.refresh();
        txtOrderId.setText(getNextOrderId());
        txtCustomerID.clear();
        txtphone.clear();
        txtMenuID.clear();
        txtItemName.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        txtPaymentID.setText(PaymentsModel.getNextPaymentId());
        txtReservationID.clear();
        choiceBoxOrderType.setValue(null);
        choiseBoxOrderStatus.setValue(null);
        txtAmount.setText("0.00");
        totalAmount = 0; // Reset total amount

    }

    @FXML
    void TelesearchOnAction(ActionEvent event) throws SQLException {

        //tele eken set wenna ona customer ----> cus id eka
        String customerMobile = txtphone.getText();
        Customerdto customerDto = CustomerModel.searchCustomer(customerMobile);
        if (customerDto != null) {
            txtphone.setText(customerDto.getCustomerPhone());
            txtCustomerID.setText(customerDto.getCustomerId());

        } else {
            new Alert(Alert.AlertType.WARNING, "Customer not found!", ButtonType.OK).showAndWait();
        }

    }

    @FXML
    void UpdateOA(ActionEvent event) {
        try {
            String orderId = txtOrderId.getText(); // Assume the order ID is filled
            String status =choiseBoxOrderStatus.getValue(); // Get the selected status

            if (orderId.isEmpty() || status == null) {
                showAlert(Alert.AlertType.WARNING, "Please fill in all required fields.");
                return;
            }

            // Update order status using the model method
            boolean isUpdated = placeOrderBO.updateOrderStatus(orderId, status);
            if (isUpdated) {
                showAlert(Alert.AlertType.INFORMATION, "Order status updated successfully!");
            } else {
                showAlert(Alert.AlertType.ERROR, "Failed to update the order status.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error updating order status: " + e.getMessage());
        }

    }


    @FXML
    void BackOA(ActionEvent event) throws IOException {
        OrderFormid.getChildren().clear();
        OrderFormid.getChildren().add(FXMLLoader.load(getClass().getResource("/view/HomePage.fxml")));
    }

    @FXML
    void itemsearchOnAction(ActionEvent event) {

        try {
            String MenuItemName = txtItemName.getText();
            Menudto menuItemDto = MenuModel.searchMenuItem(MenuItemName);
            if (menuItemDto != null) {
                txtItemName.setText(menuItemDto.getMenuname());
                txtMenuID.setText(menuItemDto.getMenuid());
                txtUnitPrice.setText(String.valueOf(menuItemDto.getPrice()));

            } else {
                new Alert(Alert.AlertType.WARNING, "Item not found!", ButtonType.OK).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void resetPage() throws SQLException {
        cartTMS.clear();
        txtOrderId.setText(getNextOrderId());
        txtCustomerID.clear();
        txtMenuID.clear();
        txtItemName.clear();
        txtQty.clear();
        txtUnitPrice.clear();
        txtPaymentID.clear();
        choiceBoxOrderType.setValue(null);
        choiseBoxOrderStatus.setValue(null);
        txtAmount.setText("0.00");
        tblOrderForm.refresh();
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void searchOnAction(ActionEvent event) {
        String orderId = txtOrderId.getText(); // Assuming orderId is entered in txtOrdersId

        try {
            Ordersdto order = ordersModel.searchOrder(orderId);

            if (order != null) {
                // Populate the text fields with order details
                txtCustomerID.setText(order.getCustomerId());
                choiceBoxOrderType.setValue(order.getOrderType());
                txtPaymentID.setText(order.getPaymentId());
                txtReservationID.setText(order.getReservationId());
                choiseBoxOrderStatus.setValue(order.getOrderStatus());
                //txtUserId.setText(order.getUserId());
                txtAmount.setText(String.valueOf(order.getTotalAmount()));
                lblOrderDate.setText(order.getOrderDate().toString());

//                btnUpdate.setDisable(false);
//                btnDelete.setDisable(false);
//                btnSave.setDisable(true);
            } else {
                // Create an alert of type INFORMATION or WARNING
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Order Not Found");
                alert.setHeaderText(null); // Optional: set this to null if no header is needed
                alert.setContentText("The order you are looking for was not found.");

                // Show the alert and wait for user action
                alert.showAndWait();
                // new Alert(Alert.AlertType.WARNING, "Order Not Found!", ButtonType.OK).showAndWait();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void calculateOrderAmount() {
        int qty = 0;
        double amount = 0;

        for(CartTm cartTM : cartTMS) {
            double newQty = cartTM.getAddToCartMenuItemQty() + qty ;
            double unitPrice = cartTM.getUnitPrice();
            amount += newQty * unitPrice;

        }
        txtAmount.setText(String.valueOf(amount));
    }

    @FXML
    void onClickTable(MouseEvent event) {
        // Get the selected item from the table
        CartTm selectedItem = tblOrderForm.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            // Populate the text fields with details from the selected item
            txtMenuID.setText(selectedItem.getMenuItemId());
            txtItemName.setText(selectedItem.getMenuItemName());
            txtQty.setText(String.valueOf(selectedItem.getAddToCartMenuItemQty()));
            txtUnitPrice.setText(String.valueOf(selectedItem.getUnitPrice()));
        }
    }


}
