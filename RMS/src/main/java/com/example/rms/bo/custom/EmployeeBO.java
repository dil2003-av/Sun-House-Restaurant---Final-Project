package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Employeedto;

import java.sql.SQLException;
import java.util.ArrayList;


public interface EmployeeBO extends SuperBo {




    ArrayList<Employeedto> getAllEmployee() throws SQLException, ClassNotFoundException;

    boolean saveEmployee(Employeedto dto) throws SQLException, ClassNotFoundException;

    //ArrayList<Employeedto> getAllEmployees() throws SQLException, ClassNotFoundException;

    boolean updateEmployee(Employeedto dto) throws SQLException, ClassNotFoundException;

    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;

    String getNextEmployeeId() throws SQLException, ClassNotFoundException;

    ArrayList<Employeedto> searchEmployee(String newValue) throws SQLException, ClassNotFoundException;
}
