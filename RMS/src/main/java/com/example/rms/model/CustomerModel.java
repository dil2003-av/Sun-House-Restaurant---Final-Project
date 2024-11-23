package com.example.rms.model;

import com.example.rms.dto.Customerdto;
import com.example.rms.dto.Deliveriesdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerModel {

    // Get the next customer ID
    public static String getNextCustomerId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT CustomerID FROM customers ORDER BY CustomerID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("C%03d", nextId);
        }
        return "C001";
    }

    // Save a customer
    public static boolean saveCustomer(Customerdto customer) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO customers (CustomerID, Name, Phone, Email, Address, UserID) VALUES (?, ?, ?, ?, ?, ?)",
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerPhone(),
                customer.getCustomerEmail(),
                customer.getCustomerAddress(),
                customer.getUserId()
        );
    }

    // Retrieve all customers
    public static ArrayList<Customerdto> getAllCustomers() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customers");
        ArrayList<Customerdto> customerList = new ArrayList<>();

        while (rst.next()) {
            customerList.add(new Customerdto(
                    rst.getString("CustomerID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("UserID")
            ));
        }
        return customerList;
    }

    // Update a customer
    public static boolean updateCustomer(Customerdto customer) throws SQLException {
        return CrudUtil.execute(
                "UPDATE customers SET Name=?, Phone=?, Email=?, Address=?, UserID=? WHERE CustomerID=?",
                customer.getCustomerName(),
                customer.getCustomerPhone(),
                customer.getCustomerEmail(),
                customer.getCustomerAddress(),
                customer.getUserId(),
                customer.getCustomerId()
        );
    }

    // Delete a customer
    public static boolean deleteCustomer(String customerId) throws SQLException {
        return CrudUtil.execute("DELETE FROM customers WHERE CustomerID=?", customerId);
    }

    // Search for a customer by ID
//    public static Customerdto searchCustomer(String customerId) throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM deliveries WHERE CustomerID =?", customerId);
//
//        if (rst.next()) {
//            return new Customerdto(
//                    rst.getString("CustomerID"),
//                    rst.getString("CustomerName"),
//                    rst.getString("CustomerPhone"),
//                    rst.getString("CustomerEmail"),
//                    rst.getString("CustomerAddress")
//            );
//        }
//        return null;
//    }

//    public static Customerdto searchCustomerMobile(String customerMobile) throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM customers WHERE CustomerPhone=?", customerMobile);
//
//        if (rst.next()) {
//            return new Customerdto(
//                    rst.getString("CustomerID"),
//                    rst.getString("CustomerName"),
//                    rst.getString("CustomerPhone"),
//                    rst.getString("CustomerEmail"),
//                    rst.getString("CustomerAddress")
//            );
//        }
//        return null;
//    }

    public static Customerdto searchCustomer(String customerId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM customers WHERE CustomerID=?", customerId);

        if (rst.next()) {
            return new Customerdto(
                    rst.getString("CustomerID"),
                    rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("UserID")
            );
        }
        return null;
    }
}
