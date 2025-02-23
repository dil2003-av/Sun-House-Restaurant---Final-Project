package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.TablesAssingmentsdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TablesAssignmentBO extends SuperBo {
    String getNextTableAssignmentId() throws SQLException, ClassNotFoundException;

    boolean saveTableAssignment(TablesAssingmentsdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<TablesAssingmentsdto> getAllTableAssignments() throws SQLException, ClassNotFoundException;

    boolean updateTableAssignment(TablesAssingmentsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteTableAssignment(String tableAssignmentId) throws SQLException, ClassNotFoundException;

    ArrayList<TablesAssingmentsdto> searchTableAssignment(String tableAssignmentId) throws SQLException, ClassNotFoundException;
}
