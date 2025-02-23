package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.OrderItemBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.OrderItemDAO;
//mport com.example.rms.dao.custom.OrderItemsDAO;
import com.example.rms.dto.OrderItemsdto;
import com.example.rms.entity.OrderItems;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderItemBOImpl implements OrderItemBO {

    OrderItemDAO orderItemsDAO = (OrderItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.ORDER_ITEM);

    @Override
    public String getNextOrderItemId() throws SQLException, ClassNotFoundException {
        return orderItemsDAO.getNextId();
    }

    @Override
    public boolean saveOrderItem(OrderItemsdto dto) throws SQLException, ClassNotFoundException {
        OrderItems orderItem = new OrderItems(dto.getOrderItemId(), dto.getOrderId(), dto.getMenuItemId(), dto.getQuantity(), dto.getPrice());
        return orderItemsDAO.save(orderItem);
    }

    @Override
    public ArrayList<OrderItemsdto> getAllOrderItems() throws SQLException, ClassNotFoundException {
        ArrayList<OrderItems> orderItemsList = orderItemsDAO.getAll();
        ArrayList<OrderItemsdto> orderItemsDtos = new ArrayList<>();

        for (OrderItems orderItem : orderItemsList) {
            orderItemsDtos.add(new OrderItemsdto(orderItem.getOrderItemId(), orderItem.getOrderId(), orderItem.getMenuItemId(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return orderItemsDtos;
    }

    @Override
    public boolean updateOrderItem(OrderItemsdto dto) throws SQLException, ClassNotFoundException {
        OrderItems orderItem = new OrderItems(dto.getOrderItemId(), dto.getOrderId(), dto.getMenuItemId(), dto.getQuantity(), dto.getPrice());
        return orderItemsDAO.update(orderItem);
    }

    @Override
    public boolean deleteOrderItem(String orderItemId) throws SQLException, ClassNotFoundException {
        orderItemsDAO.delete(orderItemId);
        return true;
    }

    @Override
    public ArrayList<OrderItemsdto> searchOrderItem(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<OrderItems> orderItemsList = orderItemsDAO.search(newValue);
        ArrayList<OrderItemsdto> orderItemsDtos = new ArrayList<>();

        for (OrderItems orderItem : orderItemsList) {
            orderItemsDtos.add(new OrderItemsdto(orderItem.getOrderItemId(), orderItem.getOrderId(), orderItem.getMenuItemId(), orderItem.getQuantity(), orderItem.getPrice()));
        }
        return orderItemsDtos;
    }
}


