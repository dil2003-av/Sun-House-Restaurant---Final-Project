package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Deliveriesdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DeliveriesBO extends SuperBo {
    public  String getNextDeliveryId() throws SQLException,ClassNotFoundException;

    boolean saveDelivery(Deliveriesdto delivery) throws SQLException,ClassNotFoundException;

    ArrayList<Deliveriesdto> getAllDeliveries() throws SQLException,ClassNotFoundException;

    boolean updateDelivery(Deliveriesdto delivery) throws SQLException,ClassNotFoundException;

    boolean deleteDelivery(String deliveryId) throws SQLException,ClassNotFoundException;

    ArrayList<Deliveriesdto> searchDelivery(String deliveryId) throws SQLException,ClassNotFoundException;
}
