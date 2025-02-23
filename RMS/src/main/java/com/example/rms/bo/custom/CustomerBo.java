package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Customerdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerBo extends SuperBo {

    public String getNextCustomerId() throws SQLException, ClassNotFoundException;
    public boolean saveCustomer(Customerdto customerDTO) throws SQLException, ClassNotFoundException;
    public ArrayList<Customerdto> getAllCustomers() throws SQLException, ClassNotFoundException;
    public boolean updateCustomer(Customerdto customerDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteCustomer(String customerID) throws SQLException, ClassNotFoundException;
    public ArrayList<Customerdto> searchCustomer(String customerID) throws SQLException, ClassNotFoundException;
    //public Customerdto searchCustomerMobile(String customerMobile) throws SQLException;
}
