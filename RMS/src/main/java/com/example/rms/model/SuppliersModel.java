package com.example.rms.model;

import com.example.rms.dto.Suupliersdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersModel {

    // Get the next supplier ID
    public static String getNextSupplierId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT SupplierID FROM suppliers ORDER BY SupplierID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("S%03d", nextId);
        }
        return "S001";
    }

    // Save a supplier
    public static boolean saveSupplier(Suupliersdto supplier) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO suppliers VALUES (?, ?, ?, ?, ?, ?, ?)",
                supplier.getSupplierId(),
                supplier.getSupplierName(),
                supplier.getContactPerson(),
                supplier.getSupplierPhone(),
                supplier.getSupplierEmail(),
                supplier.getSupplierAddress(),
                supplier.getUserId()
        );
    }

    // Retrieve all suppliers
    public static ArrayList<Suupliersdto> getAllSuppliers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM suppliers");
        ArrayList<Suupliersdto> suppliersList = new ArrayList<>();

        while (rst.next()) {
            suppliersList.add(new Suupliersdto(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactPerson"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address"),
                    rst.getString("UserID")
            ));
        }
        return suppliersList;
    }

    // Update a supplier
    public static boolean updateSupplier(Suupliersdto supplier) throws SQLException {
        return CrudUtil.execute(
                "UPDATE suppliers SET Name=?, ContactPerson=?, Phone=?, Email=?, Address=?, UserID=? WHERE SupplierID=?",
                supplier.getSupplierName(),
                supplier.getContactPerson(),
                supplier.getSupplierPhone(),
                supplier.getSupplierEmail(),
                supplier.getSupplierAddress(),
                supplier.getUserId(),
                supplier.getSupplierId()
        );
    }

    // Delete a supplier
    public static boolean deleteSupplier(String supplierId) throws SQLException {
        return CrudUtil.execute("DELETE FROM suppliers WHERE SupplierID=?", supplierId);
    }

    // Search for a supplier by ID
    public static Suupliersdto searchSupplier(String supplierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM suppliers WHERE SupplierID=?", supplierId);

        if (rst.next()) {
            return new Suupliersdto(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactPerson"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address"),
                    rst.getString("UserID")
            );
        }
        return null;
    }
}
