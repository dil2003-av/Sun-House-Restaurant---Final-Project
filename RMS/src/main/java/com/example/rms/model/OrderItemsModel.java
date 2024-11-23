package com.example.rms.model;

import com.example.rms.dto.OrderItemsdto;
import com.example.rms.util.CrudUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemsModel {

    // Get the next OrderItemID
    public static String getNextOrderItemId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT OrderItemID FROM orderitems ORDER BY OrderItemID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(2);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("OI%03d", nextId);
        }
        return "OI001";
    }

    // Save a single OrderItem
    public static boolean saveOrderItem(OrderItemsdto orderItem) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO orderitems (OrderItemID, OrderID, MenuItemID, Quantity, Price) VALUES (?, ?, ?, ?, ?)",
                orderItem.getOrderItemId(),
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                Integer.parseInt(orderItem.getQuantity()),
                orderItem.getPrice()
        );
    }

    // Save multiple OrderItems
    public static boolean saveOrderItems(Connection connection, ArrayList<OrderItemsdto> orderItems) throws SQLException {
        String sql = "INSERT INTO orderitems (OrderItemID, OrderID, MenuItemID, Quantity, Price) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(sql);

        for (OrderItemsdto item : orderItems) {
            ps.setString(1, item.getOrderItemId());
            ps.setString(2, item.getOrderId());
            ps.setString(3, item.getMenuItemId());
            ps.setInt(4, Integer.parseInt(item.getQuantity()));
            ps.setDouble(5, item.getPrice());

            if (ps.executeUpdate() <= 0) return false;
        }
        return true;
    }

    // Retrieve all OrderItems
    public static ArrayList<OrderItemsdto> getAllOrderItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orderitems");
        ArrayList<OrderItemsdto> orderItemsList = new ArrayList<>();

        while (rst.next()) {
            orderItemsList.add(new OrderItemsdto(
                    rst.getString("OrderItemID"),
                    rst.getString("OrderID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Quantity"),
                    rst.getDouble("Price")
            ));
        }
        return orderItemsList;
    }

    // Update an OrderItem
    public static boolean updateOrderItem(OrderItemsdto orderItem) throws SQLException {
        return CrudUtil.execute(
                "UPDATE orderitems SET OrderID=?, MenuItemID=?, Quantity=?, Price=? WHERE OrderItemID=?",
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                Integer.parseInt(orderItem.getQuantity()),
                orderItem.getPrice(),
                orderItem.getOrderItemId()
        );
    }

    // Delete an OrderItem
    public static boolean deleteOrderItem(String orderItemId) throws SQLException {
        return CrudUtil.execute("DELETE FROM orderitems WHERE OrderItemID=?", orderItemId);
    }

    // Search for an OrderItem by ID
    public static OrderItemsdto searchOrderItem(String orderItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orderitems WHERE OrderItemID=?", orderItemId);

        if (rst.next()) {
            return new OrderItemsdto(
                    rst.getString("OrderItemID"),
                    rst.getString("OrderID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Quantity"),
                    rst.getDouble("Price")
            );
        }
        return null;
    }
}
