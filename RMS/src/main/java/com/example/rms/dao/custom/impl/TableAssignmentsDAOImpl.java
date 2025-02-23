package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.TableAssignemtDAO;
//import com.example.rms.dao.custom.TableAssignmentsDAO;
//import com.example.rms.entity.TableAssignments;
import com.example.rms.entity.Employee;
import com.example.rms.entity.TablesAssingments;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TableAssignmentsDAOImpl implements TableAssignemtDAO {

    @Override
    public ArrayList<TablesAssingments> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tableassignments");
        ArrayList<TablesAssingments> tableAssignmentList = new ArrayList<>();

        while (rst.next()) {
            TablesAssingments tableAssignment = new TablesAssingments(
                    rst.getString("TableAssignmentID"),
                    rst.getString("TableID"),
                    rst.getString("ReservationID"),
                    rst.getTimestamp("AssignmentTime").toLocalDateTime()
            );
            tableAssignmentList.add(tableAssignment);
        }
        return tableAssignmentList;
    }



    @Override
    public boolean save(TablesAssingments tableAssignment) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO tableassignments (TableAssignmentID, TableID, ReservationID, AssignmentTime) VALUES (?, ?, ?, ?)",
                tableAssignment.getTableAssignmentId(),
                tableAssignment.getTableId(),
                tableAssignment.getReservationId(),
                tableAssignment.getAssessmentDate()
        );
    }

    @Override
    public boolean update(TablesAssingments tableAssignment) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE tableassignments SET TableID=?, ReservationID=?, AssignmentTime=? WHERE TableAssignmentID=?",
                tableAssignment.getTableId(),
                tableAssignment.getReservationId(),
                tableAssignment.getAssessmentDate(),
                tableAssignment.getTableAssignmentId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT TableAssignmentID FROM tableassignments WHERE TableAssignmentID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String tableAssignmentId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM tableassignments WHERE TableAssignmentID=?", tableAssignmentId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT TableAssignmentID FROM tableassignments ORDER BY TableAssignmentID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "TA001"
            String numericPart = lastId.substring(2); // Remove the prefix 'TA'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("TA%03d", nextId);
        }
        return "TA001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<TablesAssingments> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM tableassignments WHERE TableAssignmentID=?", newValue);
        ArrayList<TablesAssingments> searchResults = new ArrayList<>();

        while (rst.next()) {
            TablesAssingments tableAssignment = new TablesAssingments(
                    rst.getString("TableAssignmentID"),
                    rst.getString("TableID"),
                    rst.getString("ReservationID"),
                    rst.getTimestamp("AssignmentTime").toLocalDateTime()
            );
            searchResults.add(tableAssignment);
        }
        return searchResults;
    }
}
