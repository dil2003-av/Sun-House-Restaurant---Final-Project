package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
//import com.example.rms.dao.custom.TableDAO;
import com.example.rms.dao.custom.TablesDAO;
//import com.example.rms.entity.Table;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Tables;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TablesDAOImpl implements TablesDAO {

    @Override
    public ArrayList<Tables> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tables");
        ArrayList<Tables> tablesList = new ArrayList<>();

        while (rst.next()) {
            Tables table = new Tables(
                    rst.getString("TableID"),
                    rst.getString("TableNumber"),
                    rst.getString("Capacity"),
                    rst.getString("Location"),
                    rst.getString("Status")
            );
            tablesList.add(table);
        }
        return tablesList;
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
    public boolean save(Tables tableEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO tables (TableID, TableNumber, Capacity, Location, Status) VALUES (?, ?, ?, ?, ?)",
                tableEntity.getTableId(),
                tableEntity.getTableNumber(),
                tableEntity.getTableCapacity(),
                tableEntity.getTableLocation(),
                tableEntity.getTableStatus()
        );
    }

    @Override
    public boolean update(Tables tableEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE tables SET TableNumber=?, Capacity=?, Location=?, Status=? WHERE TableID=?",
                tableEntity.getTableNumber(),
                tableEntity.getTableCapacity(),
                tableEntity.getTableLocation(),
                tableEntity.getTableStatus(),
                tableEntity.getTableId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT TableID FROM tables WHERE TableID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String tableId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM tables WHERE TableID=?", tableId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT TableID FROM tables ORDER BY TableID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "T001"
            String numericPart = lastId.substring(1); // Remove the prefix letter 'T'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("T%03d", nextId);
        }
        return "T001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<Tables> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM tables WHERE TableID=?", newValue);
        ArrayList<Tables> searchResults = new ArrayList<>();

        while (rst.next()) {
            Tables table = new Tables(
                    rst.getString("TableID"),
                    rst.getString("TableNumber"),
                    rst.getString("Capacity"),
                    rst.getString("Location"),
                    rst.getString("Status")
            );
            searchResults.add(table);
        }
        return searchResults;
    }
}
