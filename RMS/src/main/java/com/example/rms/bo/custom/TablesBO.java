package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Tablesdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface TablesBO extends SuperBo {
    String getNextTableId() throws SQLException, ClassNotFoundException;

    boolean saveTable(Tablesdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Tablesdto> getAllTables() throws SQLException, ClassNotFoundException;

    boolean updateTable(Tablesdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteTable(String tableId) throws SQLException, ClassNotFoundException;
}
