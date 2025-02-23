package com.example.rms.dao;

import com.example.rms.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO<T> extends SuperDAO {
    public ArrayList<T> getAll() throws SQLException, ClassNotFoundException;

    public boolean save(T Dto) throws SQLException, ClassNotFoundException;

    public boolean update(T Dto) throws SQLException, ClassNotFoundException;

    public boolean existId(String id) throws SQLException, ClassNotFoundException;

    public void delete(String id) throws SQLException, ClassNotFoundException;

    public String getNextId() throws SQLException, ClassNotFoundException;

    public ArrayList<T> search(String newValue) throws SQLException, ClassNotFoundException;
}

