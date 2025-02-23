package com.example.rms.model;

import com.example.rms.dto.InventoryItemsdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItemsModel {

//    // Get the next inventory item ID
//    public static String getNextInventoryItemId() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT InventoryItemID FROM inventoryitems ORDER BY InventoryItemID DESC LIMIT 1");
//
//        if (rst.next()) {
//            String lastId = rst.getString(1).substring(1);
//            int nextId = Integer.parseInt(lastId) + 1;
//            return String.format("I%03d", nextId);
//        }
//        return "I001";
//    }
//
//    // Save an inventory item
//    public static boolean saveInventoryItem(InventoryItemsdto item) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO inventoryitems VALUES (?, ?, ?, ?, ?)",
//                item.getInventoryItemId(),
//                item.getInventoryName(),
//                item.getInventoryDescription(),
//                item.getInventoryQuantity(),
//                item.getInventoryUnit()
//        );
//    }
//
//    // Retrieve all inventory items
//    public static ArrayList<InventoryItemsdto> getAllInventoryItems() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM inventoryitems");
//        ArrayList<InventoryItemsdto> inventoryList = new ArrayList<>();
//
//        while (rst.next()) {
//            inventoryList.add(new InventoryItemsdto(
//                    rst.getString("InventoryItemID"),
//                    rst.getString("Name"),
//                    rst.getString("Description"),
//                    rst.getString("Quantity"),
//                    rst.getString("Unit")
//            ));
//        }
//        return inventoryList;
//    }
//
//    // Update an inventory item
//    public static boolean updateInventoryItem(InventoryItemsdto item) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE inventoryitems SET Name=?, Description=?, Quantity=?, Unit=? WHERE InventoryItemID=?",
//                item.getInventoryName(),
//                item.getInventoryDescription(),
//                item.getInventoryQuantity(),
//                item.getInventoryUnit(),
//                item.getInventoryItemId()
//        );
//    }
//
//    // Delete an inventory item
//    public static boolean deleteInventoryItem(String itemId) throws SQLException {
//        return CrudUtil.execute("DELETE FROM inventoryitems WHERE InventoryItemID=?", itemId);
//    }

    // Search for an inventory item by ID
    public static InventoryItemsdto searchInventoryItem(String itemId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM inventoryitems WHERE InventoryItemID=?", itemId);

        if (rst.next()) {
            return new InventoryItemsdto(
                    rst.getString("InventoryItemID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getString("Quantity"),
                    rst.getString("Unit")
            );
        }
        return null;
    }
}
