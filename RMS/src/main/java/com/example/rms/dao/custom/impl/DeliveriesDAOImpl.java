package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.DeliveriesDAO;
import com.example.rms.entity.Deliveries;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveriesDAOImpl implements DeliveriesDAO {
    @Override
    public ArrayList<Deliveries> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM deliveries");
        ArrayList<Deliveries> deliveriesList = new ArrayList<>();

        while (rst.next()) {
            Deliveries delivery = new Deliveries(
                    rst.getString("DeliveryID"),
                    rst.getString("OrderID"),
                    rst.getDate("DeliveryDate"),
                    rst.getString("DeliveryStatus"),
                    rst.getString("DeliveryAddress")
            );
            deliveriesList.add(delivery);
        }
        return deliveriesList;
    }

    @Override
    public boolean save(Deliveries delivery) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO deliveries VALUES (?, ?, ?, ?, ?)",
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStatus(),
                delivery.getDeliveryAddress()
        );
    }

    @Override
    public boolean update(Deliveries delivery) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE deliveries SET OrderID=?, DeliveryDate=?, DeliveryStatus=?, DeliveryAddress=? WHERE DeliveryID=?",
                delivery.getOrderId(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStatus(),
                delivery.getDeliveryAddress(),
                delivery.getDeliveryId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT DeliveryID FROM deliveries WHERE DeliveryID=?", id);
        return rst.next();  // Returns true if the ID exists
    }

    @Override
    public void delete(String deliveryId) throws SQLException, ClassNotFoundException {
         SQLUtill.execute("DELETE FROM deliveries WHERE DeliveryID=?", deliveryId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT DeliveryID FROM deliveries ORDER BY DeliveryID DESC LIMIT 1");
        if (rst.next() && rst.getString(1) != null) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("D%03d", nextId);
        }
        return "D001";
    }

    @Override
    public ArrayList<Deliveries> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM deliveries WHERE DeliveryID=?", newValue);
        ArrayList<Deliveries> deliveriesList = new ArrayList<>();

        while (rst.next()) {
            Deliveries delivery = new Deliveries(
                    rst.getString("DeliveryID"),
                    rst.getString("OrderID"),
                    rst.getDate("DeliveryDate"),
                    rst.getString("DeliveryStatus"),
                    rst.getString("DeliveryAddress")
            );
            deliveriesList.add(delivery);
        }
        return deliveriesList;
    }
}
