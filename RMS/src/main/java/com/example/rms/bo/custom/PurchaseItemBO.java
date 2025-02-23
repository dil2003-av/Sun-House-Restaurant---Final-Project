package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.PurchaseItmsdto;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseItemBO extends SuperBo {
    String getNextPurchaseItemId() throws SQLException, ClassNotFoundException;

    boolean savePurchaseItem(PurchaseItmsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<PurchaseItmsdto> getAllPurchaseItems() throws SQLException, ClassNotFoundException;

    boolean updatePurchaseItem(PurchaseItmsdto dto) throws SQLException, ClassNotFoundException;

    boolean deletePurchaseItem(String purchaseItemId) throws SQLException, ClassNotFoundException;

    ArrayList<PurchaseItmsdto> searchPurchaseItem(String newValue) throws SQLException, ClassNotFoundException;
}
