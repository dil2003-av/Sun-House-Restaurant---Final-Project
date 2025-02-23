package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Suupliersdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SuppliersBO extends SuperBo {
    String getNextSupplierId() throws SQLException, ClassNotFoundException;

    boolean saveSupplier(Suupliersdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Suupliersdto> getAllSuppliers() throws SQLException, ClassNotFoundException;

    boolean updateSupplier(Suupliersdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException;

    ArrayList<Suupliersdto> searchSupplier(String newValue) throws SQLException, ClassNotFoundException;
}
