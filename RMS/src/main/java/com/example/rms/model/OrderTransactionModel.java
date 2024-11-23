//package com.example.rms.model;
//
//import com.example.rms.db.DBConnection;
//import com.example.rms.dto.*;
//import com.example.rms.util.CrudUtil;
//import javafx.scene.control.Alert;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class OrderTransactionModel {
//
//    // @PaymentModel: A reference to the OrderDetailsModel to handle order details saving
//    private final PaymentsModel paymentsModel = new PaymentsModel();
//    private final InventoryItemsModel inventoryItemsModel = new InventoryItemsModel();
//    private final MenuModel menuModel = new MenuModel();
//    private final MenuItemIngredientsModel menuItemIngredientsModel = new MenuItemIngredientsModel();
//
//    // Method to get the next order ID based on an incremental pattern
//    public static String getNextOrderId() throws SQLException {
//        ResultSet resultSet = CrudUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");
//
//        if (resultSet.next()) {
//            String lastId = resultSet.getString("OrderID").substring(1);
//            int nextId = Integer.parseInt(lastId) + 1;
//            return String.format("O%03d", nextId);
//        }
//        return "O001";
//    }
//
//
//public boolean placeOrder(OrderViewDto orderDto, ArrayList<OrderItemDto> orderItemDtos, ArrayList<PaymentDto> paymentDtos) throws SQLException {
//    Connection connection = DBConnection.getInstance().getConnection();
//
//    try {
//        connection.setAutoCommit(false);
//
//        // Validate input data
//        if (orderDto == null || orderItemDtos == null || paymentDtos == null) {
//            throw new IllegalArgumentException("Order, Order Items, or Payments data is missing.");
//        }
//        if (orderItemDtos.isEmpty()) {
//            throw new IllegalArgumentException("No order items provided.");
//        }
//        if (paymentDtos.isEmpty()) {
//            throw new IllegalArgumentException("No payment details provided.");
//        }
//
//        // Step 1: Save Payments
//        for (PaymentDto paymentDto : paymentDtos) {
//            if (paymentDto.getAmount() <= 0) {
//                throw new IllegalArgumentException("Invalid payment amount: " + paymentDto.getAmount());
//            }
//
//            boolean savePayment = CrudUtil.execute(
//                    "INSERT INTO Payments VALUES (?, ?, ?, ?)",
//                    paymentDto.getId(),
//                    paymentDto.getMethod(),
//                    paymentDto.getAmount(),
//                    paymentDto.getDate()
//            );
//            if (!savePayment) {
//                connection.rollback();
//                alertUser("Failed to save payment for ID: " + paymentDto.getId());
//                return false;
//            }
//        }
//
//        // Step 2: Save Order
//        String sql = "INSERT INTO orders (OrderID, CustomerID, UserID, OrderDate, TotalAmount, Status, OrderType, ReservationID, PaymentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
//        boolean saveOrder = CrudUtil.execute(sql,
//                orderDto.getOrderId(),
//                orderDto.getCustomerId(),
//                orderDto.getUserId(),
//                orderDto.getOrderDate(),
//                orderDto.getTotalAmount(),
//                orderDto.getOrderStatus(),
//                orderDto.getOrderType(),
//                orderDto.getReservationId(),
//                orderDto.getPaymentId()
//        );
//        if (!saveOrder) {
//            connection.rollback();
//            alertUser("Failed to save order with ID: " + orderDto.getOrderId());
//            return false;
//        }
//
//        // Step 3: Save Order Items and Update Inventory
//        for (OrderItemDto orderItemDto : orderItemDtos) {
//            if (orderItemDto.getQuantity() <= 0) {
//                throw new IllegalArgumentException("Invalid quantity for MenuItemID: " + orderItemDto.getMenuItemID());
//            }
//            if (orderItemDto.getPrice() <= 0) {
//                throw new IllegalArgumentException("Invalid price for MenuItemID: " + orderItemDto.getMenuItemID());
//            }
//
//            String sql1 = "INSERT INTO OrderItems (OrderID, MenuItemID, Quantity, Price) VALUES (?, ?, ?, ?)";
//            boolean saveOrderItem = CrudUtil.execute(sql1,
//                    orderItemDto.getOrderId(),
//                    orderItemDto.getMenuItemID(),
//                    orderItemDto.getQuantity(),
//                    orderItemDto.getPrice()
//            );
//            if (!saveOrderItem) {
//                connection.rollback();
//                alertUser("Failed to save order item with MenuItemID: " + orderItemDto.getMenuItemID());
//                return false;
//            }
//
//            // Update inventory
//            boolean updateInventory = inventoryItemUpdate(orderItemDto.getMenuItemID(), orderItemDto.getQuantity());
//            if (!updateInventory) {
//                connection.rollback();
//                alertUser("Failed to update inventory for MenuItemID: " + orderItemDto.getMenuItemID());
//                return false;
//            }
//        }
//
//        connection.commit();
//        alertUser("Order placed successfully!");
//        return true;
//
//    } catch (IllegalArgumentException e) {
//        alertUser("Validation error: " + e.getMessage());
//        throw e;
//    } catch (Exception e) {
//        if (connection != null) {
//            connection.rollback();
//        }
//        alertUser("Transaction failed: " + e.getMessage());
//        throw new SQLException("Transaction failed: " + e.getMessage(), e);
//    } finally {
//        connection.setAutoCommit(true);
//    }
//}
//
//
//    public boolean inventoryItemUpdate(String menuItemId, double orderQty) {
//        //5 menuItemIngredient
//        try {
//            String sql = "SELECT InventoryItemID, QuantityNeeded FROM MenuItemIngredients WHERE MenuItemID = ?";
//            ResultSet rst = CrudUtil.execute(sql, menuItemId);
//            boolean allUpdatesSuccessful = true;
//
//            while (rst.next()) {
//                String inventoryItemId = rst.getString("InventoryItemID");
//                double qtyNeeded = rst.getDouble("QuantityNeeded"); // Use getDouble for accuracy
//                double qtyUse = orderQty * qtyNeeded;
//
//                String sql2 = "UPDATE InventoryItems SET Quantity = Quantity - ? WHERE InventoryItemID = ?";
//                boolean updateSuccess = CrudUtil.execute(sql2, qtyUse, inventoryItemId);
//
//                if (!updateSuccess) {
//                    allUpdatesSuccessful = false; // Track if any update fails
//                }
//            }
//
//            return allUpdatesSuccessful;
//        } catch (Exception e) {
//            throw new RuntimeException("Error updating inventory items", e);
//        }
//    }
//
//    // Method to display alert to the user
//    private void alertUser(String message) {
//        // Create an alert of type ERROR or INFORMATION based on message type
//        Alert alert = new Alert(Alert.AlertType.INFORMATION); // You can change AlertType based on message importance
//        alert.setTitle("Order Status");
//        alert.setHeaderText(null);  // No header text
//        alert.setContentText(message);  // The message to display in the alert
//
//        alert.showAndWait();  // Display the alert and wait for user to close it
//    }
//
//    public boolean updateOrderStatus(String orderId, String status) {
//        try {
//            String sql = "UPDATE orders SET Status = ? WHERE OrderID = ?";
//            return CrudUtil.execute(sql, status, orderId);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to update the order status: " + orderId, e);
//        }
//    }
//
//
///
//}