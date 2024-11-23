package com.example.rms.model;

import com.example.rms.dto.Tablesdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TablesModel {

    // Get the next table ID
    public static String getNextTableId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT TableID FROM tables ORDER BY TableID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("T%03d", nextId);
        }
        return "T001";
    }

    // Save a table
    public static boolean saveTable(Tablesdto table) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO tables (TableID, TableNumber, Capacity, Location, Status) VALUES (?, ?, ?, ?, ?)",
                table.getTableId(),
                table.getTableNumber(),
                table.getTableCapacity(),
                table.getTableLocation(),
                table.getTableStatus()
        );
    }

    // Retrieve all tables
    public static ArrayList<Tablesdto> getAllTables() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tables");
        ArrayList<Tablesdto> tablesList = new ArrayList<>();

        while (rst.next()) {
            tablesList.add(new Tablesdto(
                    rst.getString("TableID"),
                    rst.getString("TableNumber"),
                    rst.getString("Capacity"),
                    rst.getString("Location"),
                    rst.getString("Status")
            ));
        }
        return tablesList;
    }

    // Update a table
    public static boolean updateTable(Tablesdto table) throws SQLException {
        return CrudUtil.execute(
                "UPDATE tables SET TableNumber=?, Capacity=?, Location=?, Status=? WHERE TableID=?",
                table.getTableNumber(),
                table.getTableCapacity(),
                table.getTableLocation(),
                table.getTableStatus(),
                table.getTableId()
        );
    }

    // Delete a table
    public static boolean deleteTable(String tableId) throws SQLException {
        return CrudUtil.execute("DELETE FROM tables WHERE TableID=?", tableId);
    }

    // Search for a table by ID
    public static Tablesdto searchTable(String tableId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tables WHERE TableID=?", tableId);

        if (rst.next()) {
            return new Tablesdto(
                    rst.getString("TableID"),
                    rst.getString("TableNumber"),
                    rst.getString("Capacity"),
                    rst.getString("Location"),
                    rst.getString("Status")
            );
        }
        return null;
    }
}
