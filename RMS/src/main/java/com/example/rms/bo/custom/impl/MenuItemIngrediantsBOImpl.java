package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.MenuItemIngrediantsBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.MenuItemIngrediantsDAO;
import com.example.rms.dto.MenuItemIngredientsdto;
import com.example.rms.entity.MenuItemIngredients;

import java.sql.SQLException;
import java.util.ArrayList;

public class MenuItemIngrediantsBOImpl implements MenuItemIngrediantsBO {

    MenuItemIngrediantsDAO menuItemIngrediantsDAO = (MenuItemIngrediantsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MENU_ITEM_INGREDIANT);

    @Override
    public boolean saveMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "INSERT INTO MenuItemIngredients (MenuItemID, InventoryItemID, QuantityNeeded) VALUES (?, ?, ?)",
//                dto.getMenuItemId(),
//                dto.getInventoryItemId(),
//                dto.getQuantity()
//        );
        MenuItemIngredients mi = new MenuItemIngredients(dto.getMenuItemId(),dto.getInventoryItemId(),dto.getQuantity());
        return menuItemIngrediantsDAO.save(mi);

    }

    // Update an existing MenuItemIngredient
    @Override
    public boolean updateMenuItemIngredient(MenuItemIngredientsdto dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE MenuItemIngredients SET QuantityNeeded = ? WHERE MenuItemID = ? AND InventoryItemID = ?",
//                dto.getQuantity(),
//                dto.getMenuItemId(),
//                dto.getInventoryItemId()
//        );
        MenuItemIngredients mi = new MenuItemIngredients(dto.getMenuItemId(),dto.getInventoryItemId(),dto.getQuantity());
        return menuItemIngrediantsDAO.update(mi);

    }

    // Delete a MenuItemIngredient
    @Override
    public boolean deleteMenuItemIngredient(String menuItemId, String inventoryItemId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "DELETE FROM MenuItemIngredients WHERE MenuItemID = ? AND InventoryItemID = ?",
//                menuItemId,
//                inventoryItemId
//        );
        menuItemIngrediantsDAO.delete(menuItemId);
        return false;
    }

    // Search for a MenuItemIngredient

    @Override
    public ArrayList<MenuItemIngredientsdto> searchMenuItemIngredient(String newValue) throws SQLException, ClassNotFoundException {
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
        ArrayList<MenuItemIngredients> menuItemIngredients = menuItemIngrediantsDAO.search(newValue);
        ArrayList<MenuItemIngredientsdto> menuItemIngredientsdtos = new ArrayList<>();
        for (MenuItemIngredients item : menuItemIngredients) {
            MenuItemIngredientsdto menuItemIngredientsdto = new MenuItemIngredientsdto();
            menuItemIngredientsdto.setMenuItemId(item.getMenuItemId());
            menuItemIngredientsdto.setInventoryItemId(item.getInventoryItemId());
            menuItemIngredientsdto.setQuantity(item.getQuantity());
            menuItemIngredientsdtos.add(menuItemIngredientsdto);
        }
        return menuItemIngredientsdtos;
    }

    // Get all MenuItemIngredients
    @Override
    public ArrayList<MenuItemIngredientsdto> getAllMenuItemIngredients() throws SQLException, ClassNotFoundException {
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

        ArrayList<MenuItemIngredients> menuItemIngredients = menuItemIngrediantsDAO.getAll();
        ArrayList<MenuItemIngredientsdto> menuItemIngredientsdtos = new ArrayList<>();
        for (MenuItemIngredients item : menuItemIngredients) {
            MenuItemIngredientsdto menuItemIngredientsdto = new MenuItemIngredientsdto();
            menuItemIngredientsdto.setMenuItemId(item.getMenuItemId());
            menuItemIngredientsdto.setInventoryItemId(item.getInventoryItemId());
            menuItemIngredientsdto.setQuantity(item.getQuantity());
            menuItemIngredientsdtos.add(menuItemIngredientsdto);
        }
        return menuItemIngredientsdtos;
    }
}
