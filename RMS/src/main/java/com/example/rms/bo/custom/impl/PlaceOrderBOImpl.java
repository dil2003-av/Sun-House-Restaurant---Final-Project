package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.PlaceOrderBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.*;
import com.example.rms.db.DBConnection;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.dto.Ordersdto;
import com.example.rms.dto.Paymentsdto;
//import com.example.rms.entity.Order;
//import com.example.rms.entity.OrderItem;
import com.example.rms.entity.OrderItems;
import com.example.rms.entity.Orders;
import com.example.rms.entity.Payments;
import com.example.rms.util.CrudUtil;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlaceOrderBOImpl implements PlaceOrderBO {
    private final OrderFormDAO orderFormDAO = (OrderFormDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDERS);
    private final PaymentsDAO paymentsDAO = (PaymentsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PAYMENTS);
    private final InventoryItemsDAO inventoryItemsDAO = (InventoryItemsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY_ITEM);
    private final OrderItemDAO orderItemsDAO = (OrderItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_ITEM);

    public  String getNextOrderId() throws SQLException, ClassNotFoundException {

        return orderFormDAO.getNextId();
    }

    @Override
    public String getNextPaymentID() throws SQLException, ClassNotFoundException {
        return paymentsDAO.getNextId();
    }


    public boolean placeOrder(Ordersdto orderDto, ArrayList<OrderItemsdto> orderItemDtos, ArrayList<Paymentsdto> paymentDtos) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        try {
            connection.setAutoCommit(false);

            // Validate input data
            if (orderDto == null || orderItemDtos == null || paymentDtos == null) {
                throw new IllegalArgumentException("Order, Order Items, or Payments data is missing.");
            }
            if (orderItemDtos.isEmpty()) {
                throw new IllegalArgumentException("No order items provided.");
            }
            if (paymentDtos.isEmpty()) {
                throw new IllegalArgumentException("No payment details provided.");
            }

            // Step 1: Save Payments
            for (Paymentsdto paymentDto : paymentDtos) {
                if (paymentDto.getPaymentAmount() <= 0) {
                    throw new IllegalArgumentException("Invalid payment amount: " + paymentDto.getPaymentAmount());
                }

                boolean savePayment = paymentsDAO.save(new Payments(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentMethod(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getPaymentDate()
                ));

                if (!savePayment) {
                    connection.rollback();
                    alertUser("Failed to save payment for ID: " + paymentDto.getPaymentId());
                    return false;
                }
            }

            System.out.println("Step 1: Payments saved successfully.");

            // Step 2: Save Order
            //String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount, Status, OrderType, ReservationID, PaymentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            boolean saveOrder = orderFormDAO.save(new Orders(
                    orderDto.getOrderId(),
                    orderDto.getCustomerId(),
                    orderDto.getOrderDate(),
                    orderDto.getTotalAmount(),
                    orderDto.getOrderStatus(),
                    orderDto.getOrderType(),
                    orderDto.getReservationId(),
                    orderDto.getPaymentId()

            ));
            if (!saveOrder) {
                connection.rollback();
                alertUser("Failed to save order with ID: " + orderDto.getOrderId());
                return false;
            }

            System.out.println("Step 2: Order saved successfully.");

            // Step 3: Save Order Items and Update Inventory
            for (OrderItemsdto orderItemsdto : orderItemDtos) {
                if (false) {
                    throw new IllegalArgumentException("Invalid quantity for MenuItemID: " + orderItemsdto.getMenuItemId());
                }
                if (orderItemsdto.getPrice() <= 0) {
                    throw new IllegalArgumentException("Invalid price for MenuItemID: " + orderItemsdto.getMenuItemId());
                }

                //String sql1 = "INSERT INTO OrderItems (OrderID, MenuItemID, Quantity, Price) VALUES (?, ?, ?, ?)";
                boolean saveOrderItem = orderItemsDAO.save(new OrderItems(
                        orderItemsdto.getOrderId(),
                        orderItemsdto.getMenuItemId(),
                        orderItemsdto.getQuantity(),
                        orderItemsdto.getPrice()
                ));

                if (!saveOrderItem) {
                    connection.rollback();
                    alertUser("Failed to save order item with MenuItemID: " + orderItemsdto.getMenuItemId());
                    return false;
                }

                // Update inventory
                boolean updateInventory = inventoryItemUpdate(orderItemsdto.getMenuItemId(), Double.parseDouble(orderItemsdto.getQuantity()));
                if (!updateInventory) {
                    connection.rollback();
                    alertUser("Failed to update inventory for MenuItemID: " + orderItemsdto.getMenuItemId());
                    return false;
                }
            }

            System.out.println("Step 3: Order items saved and inventory updated.");

            connection.commit();
            alertUser("Order placed successfully!");
            return true;

        } catch (IllegalArgumentException e) {
            alertUser("Validation error: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            if (connection != null) {
                connection.rollback();
            }
            alertUser("Transaction failed: " + e.getMessage());
            throw new SQLException("Transaction failed: " + e.getMessage(), e);
        } finally {
            connection.setAutoCommit(true);
        }
    }

    @Override
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentsDAO.getNextId();
    }

    @Override
    public ArrayList<OrderItems> orderSearch(String orderId) throws SQLException, ClassNotFoundException {
        return orderItemsDAO.search(orderId);
    }

//    private boolean inventoryItemUpdate(String menuItemId, String quantity) {
//    }

    public boolean inventoryItemUpdate(String menuItemId, double orderQty) {
        try {
            String sql = "SELECT InventoryItemID, QuantityNeeded FROM MenuItemIngredients WHERE MenuItemID = ?";
            ResultSet rst = CrudUtil.execute(sql, menuItemId);
            boolean allUpdatesSuccessful = true;

            while (rst.next()) {
                String inventoryItemId = rst.getString("InventoryItemID");
                double qtyNeeded = rst.getDouble("QuantityNeeded");
                double qtyUse = orderQty * qtyNeeded;

                String sql2 = "UPDATE InventoryItems SET Quantity = Quantity - ? WHERE InventoryItemID = ?";
                boolean updateSuccess = CrudUtil.execute(sql2, qtyUse, inventoryItemId);

                if (!updateSuccess) {
                    allUpdatesSuccessful = false;
                }
            }

            return allUpdatesSuccessful;
        } catch (Exception e) {
            throw new RuntimeException("Error updating inventory items", e);
        }
    }

    private void alertUser(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Order Status");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public boolean updateOrderStatus(String orderId, String status) {
        try {
            String sql = "UPDATE orders SET Status = ? WHERE OrderID = ?";
            return CrudUtil.execute(sql, status, orderId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update the order status: " + orderId, e);
        }
    }
}
