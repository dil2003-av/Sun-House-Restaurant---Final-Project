package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.MenuItemIngredientsdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface MenuItemIngrediantsBO extends SuperBo {
    boolean saveMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException, ClassNotFoundException;

    boolean updateMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteMenuItemIngredient(String menuItemId, String inventoryItemId) throws SQLException, ClassNotFoundException;

    ArrayList<MenuItemIngredientsdto> searchMenuItemIngredient(String newValue) throws SQLException, ClassNotFoundException;

    ArrayList<MenuItemIngredientsdto> getAllMenuItemIngredients() throws SQLException, ClassNotFoundException;
}
