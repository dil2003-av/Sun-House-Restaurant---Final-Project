package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.PurchaseDAO;
import com.example.rms.dto.Purchasedto;
import com.example.rms.entity.Purchase;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseDAOImpl implements PurchaseDAO {

    @Override
    public ArrayList<Purchase> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM purchases");
        ArrayList<Purchase> purchasesList = new ArrayList<>();

        while (rst.next()) {
            Purchase purchase = new Purchase(
                    rst.getString("PurchaseID"),
                    rst.getString("SupplierID"),
                    rst.getDouble("TotalAmount"),
                    rst.getDate("PurchaseDate")
            );
            purchasesList.add(purchase);
        }
        return purchasesList;
    }

    @Override
    public boolean save(Purchase purchase) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO purchases (PurchaseID, SupplierID, TotalAmount, PurchaseDate) VALUES (?, ?, ?, ?)",
                purchase.getPurchaseId(),
                purchase.getSupplierId(),
                purchase.getPurchaseTotalAmount(),
                purchase.getPurchaseDate()
        );
    }

    @Override
    public boolean update(Purchase purchase) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE purchases SET SupplierID=?, TotalAmount=?, PurchaseDate=? WHERE PurchaseID=?",
                purchase.getSupplierId(),
                purchase.getPurchaseTotalAmount(),
                purchase.getPurchaseDate(),
                purchase.getPurchaseId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false; // Implement existence check logic if needed
    }

    @Override
    public void delete(String purchaseId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM purchases WHERE PurchaseID=?", purchaseId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT PurchaseID FROM purchases ORDER BY PurchaseID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("P%03d", nextId);
        }
        return "P001";
    }

    @Override
    public ArrayList<Purchase> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM purchases WHERE PurchaseID=?", newValue);
        ArrayList<Purchase> purchasesList = new ArrayList<>();

        while (rst.next()) {
            Purchase purchase = new Purchase(
                    rst.getString("PurchaseID"),
                    rst.getString("SupplierID"),
                    rst.getDouble("TotalAmount"),
                    rst.getDate("PurchaseDate")
            );
            purchasesList.add(purchase);
        }
        return purchasesList;
    }
}
