package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.PurchaseBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.PurchaseDAO;
import com.example.rms.dto.Purchasedto;
import com.example.rms.entity.Purchase;
import com.example.rms.entity.Purchase;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseBOImpl implements PurchaseBO {

    PurchaseDAO purchaseDAO = (PurchaseDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PURCHASE);

    @Override
    public String getNextPurchaseId() throws SQLException, ClassNotFoundException {
        return purchaseDAO.getNextId();
    }

    @Override
    public boolean savePurchase(Purchasedto dto) throws SQLException, ClassNotFoundException {
        Purchase purchase = new Purchase(dto.getPurchaseId(), dto.getSupplierId(), dto.getPurchaseTotalAmount(), dto.getPurchaseDate());
        return purchaseDAO.save(purchase);
    }

    @Override
    public ArrayList<Purchasedto> getAllPurchases() throws SQLException, ClassNotFoundException {
        ArrayList<Purchase> purchases = purchaseDAO.getAll();
        ArrayList<Purchasedto> purchasesDtos = new ArrayList<>();

        for (Purchasedto purchase : purchasesDtos) {
            purchasesDtos.add(new Purchasedto(purchase.getPurchaseId(), purchase.getSupplierId(), purchase.getPurchaseTotalAmount(), purchase.getPurchaseDate()));
        }
        return purchasesDtos;
    }

    @Override
    public boolean updatePurchase(Purchasedto dto) throws SQLException, ClassNotFoundException {
        Purchase purchase = new Purchase(dto.getPurchaseId(), dto.getSupplierId(), dto.getPurchaseTotalAmount(), dto.getPurchaseDate());
        return purchaseDAO.update(purchase);
    }

    @Override
    public boolean deletePurchase(String purchaseId) throws SQLException, ClassNotFoundException {
         purchaseDAO.delete(purchaseId);
         return true;
    }
}
