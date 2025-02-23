package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.DeliveriesBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.DeliveriesDAO;
import com.example.rms.dto.Deliveriesdto;
import com.example.rms.entity.Deliveries;

import java.sql.SQLException;
import java.util.ArrayList;

public class DeliveriesBOImpl implements DeliveriesBO {

    DeliveriesDAO deliveriesDAO = (DeliveriesDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.DELIVERY);

    @Override
    public String getNextDeliveryId() throws SQLException, ClassNotFoundException {
        return deliveriesDAO.getNextId();
    }

    @Override
    public boolean saveDelivery(Deliveriesdto dto) throws SQLException, ClassNotFoundException {
        Deliveries delivery = new Deliveries(dto.getDeliveryId(), dto.getOrderId(), dto.getDeliveryDate(), dto.getDeliveryStatus(), dto.getDeliveryAddress());
        return deliveriesDAO.save(delivery);
    }

    @Override
    public ArrayList<Deliveriesdto> getAllDeliveries() throws SQLException, ClassNotFoundException {
        ArrayList<Deliveries> deliveriesList = deliveriesDAO.getAll();
        ArrayList<Deliveriesdto> deliveriesDtos = new ArrayList<>();

        for (Deliveries delivery : deliveriesList) {
            deliveriesDtos.add(new Deliveriesdto(
                    delivery.getDeliveryId(),
                    delivery.getOrderId(),
                    delivery.getDeliveryDate(),
                    delivery.getDeliveryStatus(),
                    delivery.getDeliveryAddress()
            ));
        }
        return deliveriesDtos;
    }

    @Override
    public boolean updateDelivery(Deliveriesdto dto) throws SQLException, ClassNotFoundException {
        Deliveries delivery = new Deliveries(dto.getDeliveryId(), dto.getOrderId(), dto.getDeliveryDate(), dto.getDeliveryStatus(), dto.getDeliveryAddress());
        return deliveriesDAO.update(delivery);
    }

    @Override
    public boolean deleteDelivery(String deliveryId) throws SQLException, ClassNotFoundException {
        deliveriesDAO.delete(deliveryId);
        return true;
    }

    @Override
    public ArrayList<Deliveriesdto> searchDelivery(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Deliveries> deliveries = deliveriesDAO.search(newValue);
        ArrayList<Deliveriesdto> deliveriesdtos = new ArrayList<>();

        for (Deliveries delivery : deliveries) {
            Deliveriesdto deliveriesdto = new Deliveriesdto();
            deliveriesdto.setDeliveryId(delivery.getDeliveryId());
            deliveriesdto.setOrderId(delivery.getOrderId());
            deliveriesdto.setDeliveryDate(delivery.getDeliveryDate());
            deliveriesdto.setDeliveryStatus(delivery.getDeliveryStatus());
            deliveriesdto.setDeliveryAddress(delivery.getDeliveryAddress());
            deliveriesdtos.add(deliveriesdto);
        }

        return deliveriesdtos;
    }

}
