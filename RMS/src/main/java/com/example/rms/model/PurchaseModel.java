package com.example.rms.model;

import com.example.rms.dto.Purchasedto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseModel {

    // Get the next purchase ID
    public static String getNextPurchaseId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT PurchaseID FROM purchases ORDER BY PurchaseID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("P%03d", nextId);
        }
        return "P001";
    }
//
//    // Save a purchase
//    public static boolean savePurchase(Purchasedto purchase) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO purchases VALUES (?, ?, ?, ?)",
//                purchase.getPurchaseId(),
//                purchase.getSupplierId(),
//                purchase.getPurchaseTotalAmount(),
//                purchase.getPurchaseDate()
//        );
//    }

    // Retrieve all purchases
    public static ArrayList<Purchasedto> getAllPurchases() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchases");
        ArrayList<Purchasedto> purchasesList = new ArrayList<>();

        while (rst.next()) {
            purchasesList.add(new Purchasedto(
                    rst.getString("PurchaseID"),
                    rst.getString("SupplierID"),
                    rst.getDouble("TotalAmount"),
                    rst.getDate("PurchaseDate")
            ));
        }
        return purchasesList;
    }
//
//    // Update a purchase
//    public static boolean updatePurchase(Purchasedto purchase) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE purchases SET SupplierID=?, TotalAmount=?, PurchaseDate=? WHERE PurchaseID=?",
//                purchase.getSupplierId(),
//                purchase.getPurchaseTotalAmount(),
//                purchase.getPurchaseDate(),
//                purchase.getPurchaseId()
//        );
//    }
//
//    // Delete a purchase
//    public static boolean deletePurchase(String purchaseId) throws SQLException {
//        return CrudUtil.execute("DELETE FROM purchases WHERE PurchaseID=?", purchaseId);
//    }

    // Search for a purchase by ID
    public static Purchasedto searchPurchase(String purchaseId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM purchases WHERE PurchaseID=?", purchaseId);

        if (rst.next()) {
            return new Purchasedto(
                    rst.getString("PurchaseID"),
                    rst.getString("SupplierID"),
                    rst.getDouble("TotalAmount"),
                    rst.getDate("PurchaseDate")
            );
        }
        return null;
    }
}
