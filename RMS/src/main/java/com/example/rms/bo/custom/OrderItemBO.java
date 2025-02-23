package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.OrderItemsdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderItemBO extends SuperBo {

    String getNextOrderItemId() throws SQLException, ClassNotFoundException;

    boolean saveOrderItem(OrderItemsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<OrderItemsdto> getAllOrderItems() throws SQLException, ClassNotFoundException;

    boolean updateOrderItem(OrderItemsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteOrderItem(String orderItemId) throws SQLException, ClassNotFoundException;

    ArrayList<OrderItemsdto> searchOrderItem(String newValue) throws SQLException, ClassNotFoundException;
}
