package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Purchasedto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseBO extends SuperBo {
    String getNextPurchaseId() throws SQLException, ClassNotFoundException;

    boolean savePurchase(Purchasedto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Purchasedto> getAllPurchases() throws SQLException, ClassNotFoundException;

    boolean updatePurchase(Purchasedto dto) throws SQLException, ClassNotFoundException;

    boolean deletePurchase(String purchaseId) throws SQLException, ClassNotFoundException;
}
