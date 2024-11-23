package com.example.rms.model;

import com.example.rms.dto.Ordersdto;
import com.example.rms.util.CrudUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrdersModel {

    // Get the next order ID
    public static String getNextOrderId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("O%03d", nextId);
        }
        return "O001";
    }

    // Save an order
    public static boolean saveOrder(Ordersdto order) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO orders (OrderID, CustomerID, OrderDate, TotalAmount, Status, OrderType, ReservationID, PaymentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)",
                order.getOrderId(),
                order.getCustomerId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getOrderType(),
                order.getReservationId(),
                order.getPaymentId()
        );
    }

    // Retrieve all orders
    public static ArrayList<Ordersdto> getAllOrders() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orders");
        ArrayList<Ordersdto> ordersList = new ArrayList<>();

        while (rst.next()) {
            ordersList.add(new Ordersdto(
                    rst.getString("OrderID"),
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getString("PaymentID"),
                    rst.getDate("OrderDate"),
                    rst.getDouble("TotalAmount"),
                    rst.getString("Status"),
                    rst.getString("OrderType")
            ));
        }
        return ordersList;
    }

    // Update an order
    public static boolean updateOrder(Ordersdto order) throws SQLException {
        return CrudUtil.execute(
                "UPDATE orders SET CustomerID=?, OrderDate=?, TotalAmount=?, Status=?, OrderType=?, ReservationID=?, PaymentID=? WHERE OrderID=?",
                order.getCustomerId(),
                order.getOrderDate(),
                order.getTotalAmount(),
                order.getOrderStatus(),
                order.getOrderType(),
                order.getReservationId(),
                order.getPaymentId(),
                order.getOrderId()
        );
    }

    // Delete an order
    public static boolean deleteOrder(String orderId) throws SQLException {
        return CrudUtil.execute("DELETE FROM orders WHERE OrderID=?", orderId);
    }

    // Search for an order by ID
    public static Ordersdto searchOrder(String orderId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orders WHERE OrderID=?", orderId);

        if (rst.next()) {
            return new Ordersdto(
                    rst.getString("OrderID"),
                    rst.getString("ReservationID"),
                    rst.getString("CustomerID"),
                    rst.getString("PaymentID"),
                    rst.getDate("OrderDate"),
                    rst.getDouble("TotalAmount"),
                    rst.getString("Status"),
                    rst.getString("OrderType")
            );
        }
        return null;
    }
}
