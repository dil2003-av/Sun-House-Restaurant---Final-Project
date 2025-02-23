package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.OrderItemDAO;
//import com.example.rms.dao.custom.OrderItemsDAO;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.entity.Employee;
import com.example.rms.entity.OrderItems;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemDAOImpl implements OrderItemDAO {
    @Override
    public  ArrayList<OrderItems> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orderitems");
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();

        while (rst.next()) {
            orderItemsList.add(new OrderItems(
                    rst.getString("OrderItemID"),
                    rst.getString("OrderID"),
                    rst.getString("MenuItemID"),
                    rst.getString("Quantity"),
                    rst.getDouble("Price")
            ));
        }
        return orderItemsList;
    }

    @Override
    public boolean save(OrderItems orderItem) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO orderitems VALUES (?, ?, ?, ?, ?)",
                orderItem.getOrderItemId(),
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                orderItem.getQuantity(),
                orderItem.getPrice()
        );
    }

    @Override
    public boolean update(OrderItems orderItem) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE orderitems SET OrderID=?, MenuItemID=?, Quantity=?, Price=? WHERE OrderItemID=?",
                orderItem.getOrderId(),
                orderItem.getMenuItemId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getOrderItemId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String orderItemId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM orderitems WHERE OrderItemID=?", orderItemId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT OrderItemID FROM orderitems ORDER BY OrderItemID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1);
            String numericPart = lastId.substring(2);
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("OI%03d", nextId);
        }
        return "OI001";
    }

    @Override
    public ArrayList<OrderItems> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM orderitems WHERE OrderItemID=?", newValue);
        ArrayList<OrderItems> orderItemsList = new ArrayList<>();

        while (rst.next()) {
            OrderItems orderItem = new OrderItems(
                    rst.getString("OrderItemID"),
                    rst.getString("OrderID"),
                    rst.getString("MenuItemID"),
                    rst.getInt("Quantity"),
                    rst.getDouble("Price")
            );
            orderItemsList.add(orderItem);
        }
        return orderItemsList;
    }
}
