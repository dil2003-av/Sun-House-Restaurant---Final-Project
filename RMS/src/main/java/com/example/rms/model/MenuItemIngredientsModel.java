package com.example.rms.model;

import com.example.rms.dto.MenuItemIngredientsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuItemIngredientsModel {

//    // Save a new MenuItemIngredient
//    public static boolean saveMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO MenuItemIngredients (MenuItemID, InventoryItemID, QuantityNeeded) VALUES (?, ?, ?)",
//                dto.getMenuItemId(),
//                dto.getInventoryItemId(),
//                dto.getQuantity()
//        );
//    }
//
//    // Update an existing MenuItemIngredient
//    public static boolean updateMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE MenuItemIngredients SET QuantityNeeded = ? WHERE MenuItemID = ? AND InventoryItemID = ?",
//                dto.getQuantity(),
//                dto.getMenuItemId(),
//                dto.getInventoryItemId()
//        );
//    }
//
//    // Delete a MenuItemIngredient
//    public static boolean deleteMenuItemIngredient(String menuItemId, String inventoryItemId) throws SQLException {
//        return CrudUtil.execute(
//                "DELETE FROM MenuItemIngredients WHERE MenuItemID = ? AND InventoryItemID = ?",
//                menuItemId,
//                inventoryItemId
//        );
//    }
//
//    // Search for a MenuItemIngredient
//    public static MenuItemIngredientsdto searchMenuItemIngredient(String menuItemId, String inventoryItemId) throws SQLException {
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
//        return null;
//    }
//
//    // Get all MenuItemIngredients
//    public static ArrayList<MenuItemIngredientsdto> getAllMenuItemIngredients() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM MenuItemIngredients");
//
//        ArrayList<MenuItemIngredientsdto> ingredientList = new ArrayList<>();
//
//        while (rst.next()) {
//            ingredientList.add(new MenuItemIngredientsdto(
//                    rst.getString("MenuItemID"),
//                    rst.getString("InventoryItemID"),
//                    rst.getDouble("QuantityNeeded")
//            ));
//        }
//        return ingredientList;
//    }
}
