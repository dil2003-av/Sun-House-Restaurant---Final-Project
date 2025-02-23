package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
//import com.example.rms.dao.custom.SupplierDAO;
import com.example.rms.dao.custom.SuppliersDAO;
//import com.example.rms.entity.Supplier;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Suupliers;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersDAOImpl implements SuppliersDAO {

    @Override
    public ArrayList<Suupliers> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM suppliers");
        ArrayList<Suupliers> suppliersList = new ArrayList<>();

        while (rst.next()) {
            Suupliers supplier = new Suupliers(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactPerson"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address"),
                    rst.getString("UserID")
            );
            suppliersList.add(supplier);
        }
        return suppliersList;
    }

//    @Override
//    public boolean save(Employee Dto) throws SQLException, ClassNotFoundException {
//        return false;
//    }
//
//    @Override
//    public boolean update(Employee Dto) throws SQLException, ClassNotFoundException {
//        return false;
//    }

    @Override
    public boolean save(Suupliers supplierEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO suppliers (SupplierID, Name, ContactPerson, Phone, Email, Address, UserID) VALUES (?, ?, ?, ?, ?, ?, ?)",
                supplierEntity.getSupplierId(),
                supplierEntity.getSupplierName(),
                supplierEntity.getContactPerson(),
                supplierEntity.getSupplierPhone(),
                supplierEntity.getSupplierEmail(),
                supplierEntity.getSupplierAddress(),
                supplierEntity.getUserId()
        );
    }

    @Override
    public boolean update(Suupliers supplierEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE suppliers SET Name=?, ContactPerson=?, Phone=?, Email=?, Address=?, UserID=? WHERE SupplierID=?",
                supplierEntity.getSupplierName(),
                supplierEntity.getContactPerson(),
                supplierEntity.getSupplierPhone(),
                supplierEntity.getSupplierEmail(),
                supplierEntity.getSupplierAddress(),
                supplierEntity.getUserId(),
                supplierEntity.getSupplierId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT SupplierID FROM suppliers WHERE SupplierID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String supplierId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM suppliers WHERE SupplierID=?", supplierId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT SupplierID FROM suppliers ORDER BY SupplierID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "S001"
            String numericPart = lastId.substring(1); // Remove the prefix letter 'S'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("S%03d", nextId);
        }
        return "S001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<Suupliers> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM suppliers WHERE SupplierID=?", newValue);
        ArrayList<Suupliers> searchResults = new ArrayList<>();

        while (rst.next()) {
            Suupliers supplier = new Suupliers(
                    rst.getString("SupplierID"),
                    rst.getString("Name"),
                    rst.getString("ContactPerson"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("Address"),
                    rst.getString("UserID")
            );
            searchResults.add(supplier);
        }
        return searchResults;
    }
}
