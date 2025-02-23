package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.InventoryItemsdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InventoryItemsBO extends SuperBo {
    String getNextInventoryItemId() throws SQLException, ClassNotFoundException;

    boolean saveInventoryItem(InventoryItemsdto dto) throws SQLException, ClassNotFoundException;


    ArrayList<InventoryItemsdto> getAllInventoryItems() throws SQLException, ClassNotFoundException;

    boolean updateInventoryItem(InventoryItemsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteInventoryItem(String itemId) throws SQLException, ClassNotFoundException;
}
