package com.example.rms.model;

import com.example.rms.dto.TablesAssingmentsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TablesAssignmentsModel {

    // Get the next Table Assignment ID
    public static String getNextTableAssignmentId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT TableAssignmentID FROM tableassignments ORDER BY TableAssignmentID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(2);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("TA%03d", nextId);
        }
        return "TA001";
    }

    // Save a Table Assignment
    public static boolean saveTableAssignment(TablesAssingmentsdto tableAssignment) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO tableassignments (TableAssignmentID, TableID, ReservationID, AssignmentTime) VALUES (?, ?, ?, ?)",
                tableAssignment.getTableAssignmentId(),
                tableAssignment.getTableId(),
                tableAssignment.getReservationId(),
                tableAssignment.getAssessmentDate()
        );
    }

    // Retrieve all Table Assignments
    public static ArrayList<TablesAssingmentsdto> getAllTableAssignments() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tableassignments");
        ArrayList<TablesAssingmentsdto> tableAssignmentList = new ArrayList<>();

        while (rst.next()) {
            tableAssignmentList.add(new TablesAssingmentsdto(
                    rst.getString("TableAssignmentID"),
                    rst.getString("TableID"),
                    rst.getString("ReservationID"),
                    rst.getTimestamp("AssignmentTime").toLocalDateTime()
            ));
        }
        return tableAssignmentList;
    }

    // Update a Table Assignment
    public static boolean updateTableAssignment(TablesAssingmentsdto tableAssignment) throws SQLException {
        return CrudUtil.execute(
                "UPDATE tableassignments SET TableID=?, ReservationID=?, AssignmentTime=? WHERE TableAssignmentID=?",
                tableAssignment.getTableId(),
                tableAssignment.getReservationId(),
                tableAssignment.getAssessmentDate(),
                tableAssignment.getTableAssignmentId()
        );
    }

    // Delete a Table Assignment
    public static boolean deleteTableAssignment(String tableAssignmentId) throws SQLException {
        return CrudUtil.execute("DELETE FROM tableassignments WHERE TableAssignmentID=?", tableAssignmentId);
    }

    // Search for a Table Assignment by ID
    public static TablesAssingmentsdto searchTableAssignment(String tableAssignmentId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tableassignments WHERE TableAssignmentID=?", tableAssignmentId);

        if (rst.next()) {
            return new TablesAssingmentsdto(
                    rst.getString("TableAssignmentID"),
                    rst.getString("TableID"),
                    rst.getString("ReservationID"),
                    rst.getTimestamp("AssignmentTime").toLocalDateTime()
            );
        }
        return null;
    }
}
