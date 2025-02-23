package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.CustomerBo;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.CustomerDAO;
import com.example.rms.dto.Customerdto;
import com.example.rms.entity.Customer;
import com.example.rms.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBo {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.CUSTOMER);

@Override
    public String getNextCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.getNextId();
    }

  @Override
    public boolean saveCustomer(Customerdto dto) throws SQLException, ClassNotFoundException {
        Customer customer = new Customer(dto.getCustomerId(), dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerPhone(), dto.getCustomerEmail(), dto.getUserId());
        return customerDAO.update(customer);
    }

   @Override
    public ArrayList<Customerdto> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<Customerdto> customerDTOs = new ArrayList<>();

        for (Customer customer : customers) {
            Customerdto customerdto = new Customerdto();
            customerdto.setCustomerId(customer.getCustomerId());
            customerdto.setCustomerName(customer.getCustomerName());
            customerdto.setCustomerAddress(customer.getCustomerAddress());
            customerdto.setCustomerPhone(customer.getCustomerPhone());
            customerdto.setCustomerEmail(customer.getCustomerEmail());
            customerdto.setUserId(customer.getUserId());
            customerDTOs.add(customerdto);
        }
        return customerDTOs;
    }

    @Override
    public boolean updateCustomer(Customerdto dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE customers SET Name=?, Phone=?, Email=?, Address=?, UserID=? WHERE CustomerID=?",
//                customer.getCustomerName(),
//                customer.getCustomerPhone(),
//                customer.getCustomerEmail(),
//                customer.getCustomerAddress(),
//                customer.getUserId(),
//                customer.getCustomerId()
//        );
        Customer customer = new Customer(dto.getCustomerName(), dto.getCustomerAddress(), dto.getCustomerPhone(), dto.getCustomerEmail(), dto.getUserId(),dto.getCustomerId());
        return customerDAO.update(customer);
    }

   @Override
    public boolean deleteCustomer(String customerId) throws SQLException, ClassNotFoundException {
//  return CrudUtil.execute("DELETE FROM customers WHERE CustomerID=?", customerId);
        customerDAO.delete(customerId);
       return true;
   }

@Override
    public ArrayList<Customerdto> searchCustomer(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.search(newValue);
        ArrayList<Customerdto> customerDTOs = new ArrayList<>();
        for (Customer customer : customers) {
            Customerdto customerdto = new Customerdto();
            customerdto.setCustomerId(customer.getCustomerId());
            customerdto.setCustomerName(customer.getCustomerName());
            customerdto.setCustomerAddress(customer.getCustomerAddress());
            customerdto.setCustomerPhone(customer.getCustomerPhone());
            customerdto.setCustomerEmail(customer.getCustomerEmail());
            customerdto.setUserId(customer.getUserId());
            customerDTOs.add(customerdto);
        }
        return customerDTOs;
    }
}



