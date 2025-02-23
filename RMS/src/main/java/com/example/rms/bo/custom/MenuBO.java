package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Menudto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MenuBO extends SuperBo {
    String getNextMenuItemId() throws SQLException, ClassNotFoundException;

    boolean saveMenuItem(Menudto menuDto) throws SQLException, ClassNotFoundException;

    ArrayList<Menudto> getAllMenuItems() throws SQLException, ClassNotFoundException;

    boolean updateMenuItem(Menudto menuDto) throws SQLException, ClassNotFoundException;

    boolean deleteMenuItem(String menuId) throws SQLException, ClassNotFoundException;

    ArrayList<Menudto> searchMenuItem(String newValue) throws Exception;
}
