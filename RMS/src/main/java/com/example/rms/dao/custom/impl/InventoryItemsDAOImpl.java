package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.InventoryItemsDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.InventoryItems;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItemsDAOImpl implements InventoryItemsDAO {
    @Override
    public ArrayList<InventoryItems> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM inventoryitems");
        ArrayList<InventoryItems> inventoryitems = new ArrayList<>();

        while (rst.next()) {
            InventoryItems inventoryitem = new InventoryItems(
                    rst.getString("InventoryItemID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getString("Quantity"),
                    rst.getString("Unit")
            );
            inventoryitems.add(inventoryitem);
        }
        return inventoryitems;

    }



    @Override
    public boolean save(InventoryItems inventoryItemsEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO inventoryitems VALUES (?, ?, ?, ?, ?)",
                inventoryItemsEntity.getInventoryItemId(),
                inventoryItemsEntity.getInventoryDescription(),
                inventoryItemsEntity.getInventoryName(),
                inventoryItemsEntity.getInventoryQuantity(),
                inventoryItemsEntity.getInventoryUnit()
        );

    }

    @Override
    public boolean update(InventoryItems inventoryItemsEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE inventoryitems SET Name=?, Description=?, Quantity=?, Unit=? WHERE InventoryItemID=?",
                inventoryItemsEntity.getInventoryName(),
                inventoryItemsEntity.getInventoryDescription(),
                inventoryItemsEntity.getInventoryQuantity(),
                inventoryItemsEntity.getInventoryUnit(),
                inventoryItemsEntity.getInventoryItemId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String itemId) throws SQLException, ClassNotFoundException {
      SQLUtill.execute("DELETE FROM inventoryitems WHERE InventoryItemID=?", itemId);

    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT InventoryItemID FROM inventoryitems ORDER BY InventoryItemID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1); // e.g., "I001"
            String numericPart = lastId.substring(1); // Remove the prefix letter 'I'
            int nextId = Integer.parseInt(numericPart) + 1;
            return String.format("I%03d", nextId);
        }
        return "I001"; // Default starting ID if no records exist
    }

    @Override
    public ArrayList<InventoryItems> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM inventoryitems WHERE InventoryItemID=?", newValue);

        if (rst.next()) {
            InventoryItems inventoryitem = new InventoryItems (
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
