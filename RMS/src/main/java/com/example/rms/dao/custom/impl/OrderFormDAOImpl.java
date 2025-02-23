package com.example.rms.dao.custom.impl;

import com.example.rms.dao.custom.OrderFormDAO;
import com.example.rms.entity.OrderItems;
import com.example.rms.entity.Orders;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderFormDAOImpl implements OrderFormDAO {

    @Override
    public String getNextId() throws SQLException {
        ResultSet resultSet = CrudUtil.execute("SELECT OrderID FROM orders ORDER BY OrderID DESC LIMIT 1");

        if (resultSet.next()) {
            String lastId = resultSet.getString("OrderID").substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("O%03d", nextId);
        }
        return "O001";
    }

    @Override
    public boolean save(Orders orderEntity) throws SQLException {
        String sql = "INSERT INTO Orders (OrderID, CustomerID, OrderDate, TotalAmount, Status, OrderType, ReservationID, PaymentID) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        boolean saveOrder = CrudUtil.execute(sql,
                orderEntity.getOrderId(),
                orderEntity.getCustomerId(),
                orderEntity.getOrderDate(),
                orderEntity.getTotalAmount(),
                orderEntity.getOrderStatus(),
                orderEntity.getOrderType(),
                orderEntity.getReservationId(),
                orderEntity.getPaymentId()
        );
        return saveOrder;
    }
//
//    @Override
//    public ArrayList<OrderItems> getAllData() throws SQLException {
//        return null;
//    }
//
//    @Override
//    public boolean update(OrderItems orderItemEntity) throws SQLException {
//        return false;
//    }

    @Override
    public ArrayList<Orders> getAll() throws SQLException, ClassNotFoundException {
        return null;
    }

//    @Override
//    public boolean save(Orders Dto) throws SQLException, ClassNotFoundException {
//        return false;
//    }

    @Override
    public boolean update(Orders Dto) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String customerID) throws SQLException {

    }

    @Override
    public ArrayList<Orders> search(String customerID) throws SQLException {
        return null;
    }


}
