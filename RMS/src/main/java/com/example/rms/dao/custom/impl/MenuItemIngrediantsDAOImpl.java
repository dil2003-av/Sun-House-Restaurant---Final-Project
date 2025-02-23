package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.MenuItemIngrediantsDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Menu;
import com.example.rms.entity.MenuItemIngredients;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuItemIngrediantsDAOImpl implements MenuItemIngrediantsDAO {

    @Override
    public ArrayList<MenuItemIngredients> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM menuitemingredients");

        ArrayList<MenuItemIngredients> ingredient = new ArrayList<>();

        while (rst.next()) {
            MenuItemIngredients menuItemIngredients = new MenuItemIngredients(
                    rst.getString("MenuItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getDouble("QuantityNeeded")
            );
        }
return ingredient;
    }

    @Override
    public boolean save(MenuItemIngredients MenuItemIngredientsEntity) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "INSERT INTO MenuItemIngredients (MenuItemID, InventoryItemID, QuantityNeeded) VALUES (?, ?, ?)",
//                Dto.getMenuItemId(),
//                Dto.getInventoryItemId(),
//                Dto.getQuantity()
//        );
        return SQLUtill.execute("INSERT INTO menuitemingredients; (MenuItemID, InventoryItemID, QuantityNeeded) VALUES (?, ?, ?)");
    }

    @Override
    public boolean update(MenuItemIngredients Dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE MenuItemIngredients SET QuantityNeeded = ? WHERE MenuItemID = ? AND InventoryItemID = ?",
//                dto.getQuantity(),
//                dto.getMenuItemId(),
//                dto.getInventoryItemId()
//        );
        return SQLUtill.execute("UPDATE menuitemingredients; SET QuantityNeeded = ? WHERE MenuItemID = ? AND InventoryItemID = ?");
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String id) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "DELETE FROM MenuItemIngredients WHERE MenuItemID = ? AND InventoryItemID = ?",
//                menuItemId,
//                inventoryItemId
//        );
        SQLUtill.execute("DELETE FROM menuitemingredients; WHERE MenuItemID = ? AND InventoryItemID = ?");
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        return "";
    }

    @Override
    public ArrayList<MenuItemIngredients> search(String newValue) throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute(
//                "SELECT * FROM MenuItemIngredients WHERE MenuItemID = ? AND InventoryItemID = ?",
//                menuItemId,
//                inventoryItemId
//        );
//
//        if (rst.next()) {
//            return new MenuItemIngredientsdto(
//                    rst.getString("MenuItemID"),
//                    rst.getString("InventoryItemID"),
//                    rst.getDouble("QuantityNeeded")
//            );
//        }
//        return null}
        ResultSet rst = SQLUtill.execute("SELECT * FROM menuitemingredients; WHERE MenuItemID = ? AND InventoryItemID = ?");
        ArrayList<MenuItemIngredients> ingredientDTOS= new ArrayList<>();
        while (rst.next()) {
            MenuItemIngredients menuItemIngredients = new MenuItemIngredients(
                    rst.getString("MenuItemID"),
                    rst.getString("InventoryItemID"),
                    rst.getDouble("QuantityNeeded")
            );
            ingredientDTOS.add(menuItemIngredients);
        }
        return ingredientDTOS;
    }
}

