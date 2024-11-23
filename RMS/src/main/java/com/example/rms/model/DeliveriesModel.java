package com.example.rms.model;

import com.example.rms.dto.Deliveriesdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveriesModel {

    // Get the next delivery ID
    public static String getNextDeliveryId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT DeliveryID FROM deliveries ORDER BY DeliveryID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("D%03d", nextId);
        }
        return "D001";
    }

    // Save a delivery
    public static boolean saveDelivery(Deliveriesdto delivery) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO deliveries (DeliveryID, OrderID, DeliveryDate, DeliveryStatus, DeliveryAddress) VALUES (?, ?, ?, ?, ?)",
                delivery.getDeliveryId(),
                delivery.getOrderId(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStatus(),
                delivery.getDeliveryAddress()
        );
    }

    // Retrieve all deliveries
    public static ArrayList<Deliveriesdto> getAllDeliveries() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM deliveries");
        ArrayList<Deliveriesdto> deliveriesList = new ArrayList<>();

        while (rst.next()) {
            deliveriesList.add(new Deliveriesdto(
                    rst.getString("DeliveryID"),
                    rst.getString("OrderID"),
                    rst.getDate("DeliveryDate"),
                    rst.getString("DeliveryStatus"),
                    rst.getString("DeliveryAddress")
            ));
        }
        return deliveriesList;
    }

    // Update a delivery
    public static boolean updateDelivery(Deliveriesdto delivery) throws SQLException {
        return CrudUtil.execute(
                "UPDATE deliveries SET OrderID=?, DeliveryDate=?, DeliveryStatus=?, DeliveryAddress=? WHERE DeliveryID=?",
                delivery.getOrderId(),
                delivery.getDeliveryDate(),
                delivery.getDeliveryStatus(),
                delivery.getDeliveryAddress(),
                delivery.getDeliveryId()
        );
    }

    // Delete a delivery
    public static boolean deleteDelivery(String deliveryId) throws SQLException {
        return CrudUtil.execute("DELETE FROM deliveries WHERE DeliveryID=?", deliveryId);
    }

    // Search for a delivery by ID
    public static Deliveriesdto searchDelivery(String deliveryId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM deliveries WHERE DeliveryID=?", deliveryId);

        if (rst.next()) {
            return new Deliveriesdto(
                    rst.getString("DeliveryID"),
                    rst.getString("OrderID"),
                    rst.getDate("DeliveryDate"),
                    rst.getString("DeliveryStatus"),
                    rst.getString("DeliveryAddress")
            );
        }
        return null;
    }
}
