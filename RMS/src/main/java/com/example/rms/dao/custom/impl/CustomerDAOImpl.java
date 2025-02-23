package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.CustomerDAO;
import com.example.rms.entity.Customer;
import com.example.rms.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {


    public String getNext() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT CustomerID FROM customers ORDER BY CustomerID DESC LIMIT 1");

        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }



    @Override
    public boolean save(Customer customerEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO customers (CustomerID, Name, Address,Phone, Email,  UserID) VALUES (?, ?, ?, ?, ?, ?)",
                customerEntity.getCustomerId(),
                customerEntity.getCustomerName(),
                customerEntity.getCustomerAddress(),
                customerEntity.getCustomerPhone(),
                customerEntity.getCustomerEmail(),
                customerEntity.getUserId()
        );
    }


    @Override
    public ArrayList<Customer> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM customers");
        ArrayList<Customer> customerDTOS = new ArrayList<>();

        while (rst.next()) {
            Customer customer = new Customer(
                    rst.getString("CustomerID"),
                   rst.getString("Name"),
                    rst.getString("Address"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getString("UserID")
            );
            customerDTOS.add(customer);
        }
        return customerDTOS;
    }




    @Override
    public boolean update(Customer customerEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "update Customers set Name=?,Address=?,Phone=?, Email=?,  UserID=? where CustomerID=?",
                customerEntity.getCustomerName(),
                customerEntity.getCustomerAddress(),
                customerEntity.getCustomerPhone(),
                customerEntity.getCustomerEmail(),
                customerEntity.getUserId(),
                customerEntity.getCustomerId()

        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT CustomerID FROM customers ORDER BY CustomerID DESC LIMIT 1");
        if (rst.next()){
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public  void delete(String customerID) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM customers WHERE CustomerID=?",customerID);
    }
    @Override
    public ArrayList<Customer> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM customers WHERE CustomerID=?", newValue);
      ArrayList<Customer> customerDTOs = new ArrayList<>();
      while (rst.next()) {
          Customer customer = new Customer(
          rst.getString("CustomerID"),
                  rst.getString("Name"),
                  rst.getString("Address"),
                  rst.getString("Phone"),
                  rst.getString("Email"),
                  rst.getString("UserID")
       );

customerDTOs.add(customer);
      }
        return customerDTOs;
    }

}
