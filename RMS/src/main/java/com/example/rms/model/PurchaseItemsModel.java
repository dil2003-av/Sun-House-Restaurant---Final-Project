package com.example.rms.model;

import com.example.rms.dto.PurchaseItmsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseItemsModel {

    // Get the next Purchase Item ID
    public static String getNextPurchaseItemId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PurchaseItemID FROM purchaseitems ORDER BY PurchaseItemID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(2);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("PI%03d", nextId);
        }
        return "PI001";
    }

    // Save a purchase item
    public static boolean savePurchaseItem(PurchaseItmsdto purchaseItem) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO purchaseitems (PurchaseItemID, InventoryItemID, PurchaseID, Price, Quantity, Status) VALUES (?, ?, ?, ?, ?, ?)",
                purchaseItem.getPurchaseItemId(),
                purchaseItem.getInventoryItemId(),
                purchaseItem.getPurchaseId(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getQuantity(),
                purchaseItem.getStatus()
        );
    }

    // Retrieve all purchase items
    public static ArrayList<PurchaseItmsdto> getAllPurchaseItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchaseitems");
        ArrayList<PurchaseItmsdto> purchaseItemsList = new ArrayList<>();

        while (rst.next()) {
            purchaseItemsList.add(new PurchaseItmsdto(
                    rst.getString("PurchaseItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getString("PurchaseID"),
                    rst.getDouble("Price"),
                    rst.getString("Quantity"),
                    rst.getString("Status")
            ));
        }
        return purchaseItemsList;
    }

    // Update a purchase item
    public static boolean updatePurchaseItem(PurchaseItmsdto purchaseItem) throws SQLException {
        return CrudUtil.execute(
                "UPDATE purchaseitems SET InventoryItemID=?, PurchaseID=?, Price=?, Quantity=?, Status=? WHERE PurchaseItemID=?",
                purchaseItem.getInventoryItemId(),
                purchaseItem.getPurchaseId(),
                purchaseItem.getPurchasePrice(),
                purchaseItem.getQuantity(),
                purchaseItem.getStatus(),
                purchaseItem.getPurchaseItemId()
        );
    }

    // Delete a purchase item
    public static boolean deletePurchaseItem(String purchaseItemId) throws SQLException {
        return CrudUtil.execute("DELETE FROM purchaseitems WHERE PurchaseItemID=?", purchaseItemId);
    }

    // Search for a purchase item by ID
    public static PurchaseItmsdto searchPurchaseItem(String purchaseItemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchaseitems WHERE PurchaseItemID=?", purchaseItemId);

        if (rst.next()) {
            return new PurchaseItmsdto(
                    rst.getString("PurchaseItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getString("PurchaseID"),
                    rst.getDouble("Price"),
                    rst.getString("Quantity"),
                    rst.getString("Status")
            );
        }
        return null;
    }
}
