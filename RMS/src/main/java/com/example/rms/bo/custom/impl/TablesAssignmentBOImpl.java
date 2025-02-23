package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.TablesAssignmentBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.TableAssignemtDAO;

import com.example.rms.dto.TablesAssingmentsdto;
import com.example.rms.entity.TablesAssingments;

import java.sql.SQLException;
import java.util.ArrayList;

public class TablesAssignmentBOImpl implements TablesAssignmentBO {

    TableAssignemtDAO tablesAssignmentDAO = (TableAssignemtDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TABLE_ASSIGNMENT);

    @Override
    public String getNextTableAssignmentId() throws SQLException, ClassNotFoundException {
        return tablesAssignmentDAO.getNextId();
    }

    @Override
    public boolean saveTableAssignment(TablesAssingmentsdto dto) throws SQLException, ClassNotFoundException {
        TablesAssingments tableAssignment = new TablesAssingments(dto.getTableAssignmentId(), dto.getTableId(), dto.getReservationId(), dto.getAssessmentDate());
        return tablesAssignmentDAO.save(tableAssignment);
    }

    @Override
    public ArrayList<TablesAssingmentsdto> getAllTableAssignments() throws SQLException, ClassNotFoundException {
        ArrayList<TablesAssingments> tableAssignmentsList = tablesAssignmentDAO.getAll();
        ArrayList<TablesAssingmentsdto> tableAssignmentsDtos = new ArrayList<>();

        for (TablesAssingments tableAssignment : tableAssignmentsList) {
            tableAssignmentsDtos.add(new TablesAssingmentsdto(tableAssignment.getTableAssignmentId(), tableAssignment.getTableId(), tableAssignment.getReservationId(), tableAssignment.getAssessmentDate()));
        }
        return tableAssignmentsDtos;
    }

    @Override
    public boolean updateTableAssignment(TablesAssingmentsdto dto) throws SQLException, ClassNotFoundException {
        TablesAssingments tableAssignment = new TablesAssingments(dto.getTableAssignmentId(), dto.getTableId(), dto.getReservationId(), dto.getAssessmentDate());
        return tablesAssignmentDAO.update(tableAssignment);
    }

    @Override
    public boolean deleteTableAssignment(String tableAssignmentId) throws SQLException, ClassNotFoundException {
        tablesAssignmentDAO.delete(tableAssignmentId);
        return true;
    }

    @Override
    public ArrayList<TablesAssingmentsdto> searchTableAssignment(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<TablesAssingments> tableAssignmentsList = tablesAssignmentDAO.search(newValue);
        ArrayList<TablesAssingmentsdto> tableAssignmentsDtos = new ArrayList<>();

        for (TablesAssingments tableAssignment : tableAssignmentsList) {
            tableAssignmentsDtos.add(new TablesAssingmentsdto(tableAssignment.getTableAssignmentId(), tableAssignment.getTableId(), tableAssignment.getReservationId(), tableAssignment.getAssessmentDate()));
        }
        return tableAssignmentsDtos;
    }

}