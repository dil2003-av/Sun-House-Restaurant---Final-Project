package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.SuppliersBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.SuppliersDAO;
import com.example.rms.dto.Suupliersdto;
import com.example.rms.entity.Suupliers;
//import com.example.rms.entity.Suppliers;

import java.sql.SQLException;
import java.util.ArrayList;

public class SuppliersBOImpl implements SuppliersBO {

    SuppliersDAO suppliersDAO = (SuppliersDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.SUPPLIERS);

    @Override
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return suppliersDAO.getNextId();
    }

    @Override
    public boolean saveSupplier(Suupliersdto dto) throws SQLException, ClassNotFoundException {
        Suupliers supplier = new Suupliers(dto.getSupplierId(), dto.getSupplierName(), dto.getContactPerson(),
                dto.getSupplierPhone(), dto.getSupplierEmail(), dto.getSupplierAddress(), dto.getUserId());
        return suppliersDAO.save(supplier);
    }

    @Override
    public ArrayList<Suupliersdto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<Suupliers> suppliersList = suppliersDAO.getAll();
        ArrayList<Suupliersdto> suppliersDtos = new ArrayList<>();

        for (Suupliers supplier : suppliersList) {
            suppliersDtos.add(new Suupliersdto(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getContactPerson(),
                    supplier.getSupplierPhone(),
                    supplier.getSupplierEmail(),
                    supplier.getSupplierAddress(),
                    supplier.getUserId()
            ));
        }
        return suppliersDtos;
    }

    @Override
    public boolean updateSupplier(Suupliersdto dto) throws SQLException, ClassNotFoundException {
        Suupliers supplier = new Suupliers(dto.getSupplierId(), dto.getSupplierName(), dto.getContactPerson(),
                dto.getSupplierPhone(), dto.getSupplierEmail(), dto.getSupplierAddress(), dto.getUserId());
        return suppliersDAO.update(supplier);
    }

    @Override
    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        suppliersDAO.delete(supplierId);
        return true;
    }

    @Override
    public ArrayList<Suupliersdto> searchSupplier(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Suupliers> suppliersList = suppliersDAO.search(newValue);
        ArrayList<Suupliersdto> suppliersDtos = new ArrayList<>();

        for (Suupliers supplier : suppliersList) {
            suppliersDtos.add(new Suupliersdto(
                    supplier.getSupplierId(),
                    supplier.getSupplierName(),
                    supplier.getContactPerson(),
                    supplier.getSupplierPhone(),
                    supplier.getSupplierEmail(),
                    supplier.getSupplierAddress(),
                    supplier.getUserId()
            ));
        }
        return suppliersDtos;
    }
}
