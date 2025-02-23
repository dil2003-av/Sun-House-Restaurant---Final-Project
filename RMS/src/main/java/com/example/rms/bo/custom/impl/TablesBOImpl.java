package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.TablesBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.TablesDAO;
import com.example.rms.dto.Tablesdto;
import com.example.rms.entity.Tables;

import java.sql.SQLException;
import java.util.ArrayList;

public class TablesBOImpl implements TablesBO {

    TablesDAO tablesDAO = (TablesDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.TABLES);

    @Override
    public String getNextTableId() throws SQLException, ClassNotFoundException {
        return tablesDAO.getNextId();
    }

    @Override
    public boolean saveTable(Tablesdto dto) throws SQLException, ClassNotFoundException {
        Tables table = new Tables(dto.getTableId(), dto.getTableNumber(), dto.getTableCapacity(), dto.getTableLocation(), dto.getTableStatus());
        return tablesDAO.save(table);
    }

    @Override
    public ArrayList<Tablesdto> getAllTables() throws SQLException, ClassNotFoundException {
        ArrayList<Tables> tablesList = tablesDAO.getAll();
        ArrayList<Tablesdto> tablesDtos = new ArrayList<>();

        for (Tables table : tablesList) {
            tablesDtos.add(new Tablesdto(table.getTableId(), table.getTableNumber(), table.getTableCapacity(), table.getTableLocation(), table.getTableStatus()));
        }
        return tablesDtos;
    }

    @Override
    public boolean updateTable(Tablesdto dto) throws SQLException, ClassNotFoundException {
        Tables table = new Tables(dto.getTableId(), dto.getTableNumber(), dto.getTableCapacity(), dto.getTableLocation(), dto.getTableStatus());
        return tablesDAO.update(table);
    }

    @Override
    public boolean deleteTable(String tableId) throws SQLException, ClassNotFoundException {
        tablesDAO.delete(tableId);
        return true;
    }
}
