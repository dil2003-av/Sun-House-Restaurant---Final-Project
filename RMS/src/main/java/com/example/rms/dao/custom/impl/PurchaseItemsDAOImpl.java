package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.PurchaseItemDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.PurchaseItem;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseItemsDAOImpl implements PurchaseItemDAO {

    @Override
    public ArrayList<PurchaseItem> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchaseitems");
        ArrayList<PurchaseItem> purchaseItemsList = new ArrayList<>();

        while (rst.next()) {
            PurchaseItem purchaseItem = new PurchaseItem(
                    rst.getString("PurchaseItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getString("PurchaseID"),
                    rst.getDouble("Price"),
                    rst.getString("Quantity"),
                    rst.getString("Status")
            );
            purchaseItemsList.add(purchaseItem);
        }
        return purchaseItemsList;
    }



    @Override
    public boolean save(PurchaseItem purchaseItem) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO purchaseitems (PurchaseItemID, InventoryItemID, PurchaseID, Price, Quantity, Status) VALUES (?, ?, ?, ?, ?, ?)",
                purchaseItem.getPurchaseItemId(),
                purchaseItem.getInventoryItemId(),
                purchaseItem.getPurchaseId(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getQuantity(),
                purchaseItem.getStatus()
        );
    }

    @Override
    public boolean update(PurchaseItem purchaseItem) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE purchaseitems SET InventoryItemID=?, PurchaseID=?, Price=?, Quantity=?, Status=? WHERE PurchaseItemID=?",
                purchaseItem.getInventoryItemId(),
                purchaseItem.getPurchaseId(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getQuantity(),
                purchaseItem.getStatus(),
                purchaseItem.getPurchaseItemId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false; // Implement existence check logic if needed
    }

    @Override
    public void delete(String purchaseItemId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM purchaseitems WHERE PurchaseItemID=?", purchaseItemId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT PurchaseItemID FROM purchaseitems ORDER BY PurchaseItemID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1).substring(2);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("PI%03d", nextId);
        }
        return "PI001";
    }

    @Override
    public ArrayList<PurchaseItem> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchaseitems WHERE PurchaseItemID=?", newValue);
        ArrayList<PurchaseItem> purchaseItemsList = new ArrayList<>();

        while (rst.next()) {
            PurchaseItem purchaseItem = new PurchaseItem(
                    rst.getString("PurchaseItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getString("PurchaseID"),
                    rst.getDouble("Price"),
                    rst.getString("Quantity"),
                    rst.getString("Status")
            );
            purchaseItemsList.add(purchaseItem);
        }
        return purchaseItemsList;
    }
}
