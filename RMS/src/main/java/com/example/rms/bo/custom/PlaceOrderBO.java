package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.dto.Ordersdto;
import com.example.rms.dto.Paymentsdto;
import com.example.rms.entity.OrderItems;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PlaceOrderBO extends SuperBo {
    String getNextOrderId() throws SQLException, ClassNotFoundException;

    String getNextPaymentID() throws SQLException, ClassNotFoundException;

    boolean placeOrder(Ordersdto orderDto, ArrayList<OrderItemsdto> orderItemDtos, ArrayList<Paymentsdto> paymentDtos) throws SQLException;

    String getNextPaymentId() throws SQLException, ClassNotFoundException;

    ArrayList<OrderItems> orderSearch(String orderId) throws SQLException, ClassNotFoundException;

    boolean updateOrderStatus(String orderId, String status);
}
