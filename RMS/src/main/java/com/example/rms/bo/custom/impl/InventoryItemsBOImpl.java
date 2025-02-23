package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.InventoryItemsBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.InventoryItemsDAO;
import com.example.rms.dto.InventoryItemsdto;
import com.example.rms.entity.InventoryItems;

import java.sql.SQLException;
import java.util.ArrayList;

public class InventoryItemsBOImpl implements InventoryItemsBO {

    InventoryItemsDAO inventoryItemsDAO = (InventoryItemsDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.INVENTORY_ITEM);

    @Override
    public String getNextInventoryItemId() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("SELECT InventoryItemID FROM inventoryitems ORDER BY InventoryItemID DESC LIMIT 1");
//
//        if (rst.next()) {
//            String lastId = rst.getString(1).substring(1);
//            int nextId = Integer.parseInt(lastId) + 1;
//            return String.format("I%03d", nextId);
//        }
//        return "I001";
        return inventoryItemsDAO.getNextId();
    }

    // Save an inventory item
    @Override
    public boolean saveInventoryItem(InventoryItemsdto dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "INSERT INTO inventoryitems VALUES (?, ?, ?, ?, ?)",
//                item.getInventoryItemId(),
//                item.getInventoryName(),
//                item.getInventoryDescription(),
//                item.getInventoryQuantity(),
//                item.getInventoryUnit()
//        );
        InventoryItems inventoryItems = new InventoryItems(dto.getInventoryItemId(), dto.getInventoryName(), dto.getInventoryDescription(), dto.getInventoryQuantity(), dto.getInventoryUnit());
        return inventoryItemsDAO.save(inventoryItems);
    }

    // Retrieve all inventory items
    @Override
    public ArrayList<InventoryItemsdto> getAllInventoryItems() throws SQLException, ClassNotFoundException {
        ArrayList<InventoryItems> inventoryItemsList = inventoryItemsDAO.getAll();
        ArrayList<InventoryItemsdto> inventoryItems = new ArrayList<>();
        for (InventoryItems inventoryItem : inventoryItemsList) {
            InventoryItemsdto dto = new InventoryItemsdto();
            dto.setInventoryItemId(inventoryItem.getInventoryItemId());
            dto.setInventoryName(inventoryItem.getInventoryName());
            dto.setInventoryDescription(inventoryItem.getInventoryDescription());
            dto.setInventoryQuantity(inventoryItem.getInventoryQuantity());
            dto.setInventoryUnit(inventoryItem.getInventoryUnit());

            inventoryItems.add(dto);
        }
        return inventoryItems;
    }

    // Update an inventory item
    @Override
    public boolean updateInventoryItem(InventoryItemsdto dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE inventoryitems SET Name=?, Description=?, Quantity=?, Unit=? WHERE InventoryItemID=?",
//                item.getInventoryName(),
//                item.getInventoryDescription(),
//                item.getInventoryQuantity(),
//                item.getInventoryUnit(),
//                item.getInventoryItemId()
//        );
        InventoryItems inventoryItems = new InventoryItems(dto.getInventoryItemId(), dto.getInventoryName(), dto.getInventoryDescription(), dto.getInventoryQuantity(), dto.getInventoryUnit());
        return inventoryItemsDAO.update(inventoryItems);
    }

    // Delete an inventory item
    @Override
    public boolean deleteInventoryItem(String itemId) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute("DELETE FROM inventoryitems WHERE InventoryItemID=?", itemId);
        inventoryItemsDAO.delete(itemId);
        return true;
    }

    // Search for an inventory item by ID
    public ArrayList<InventoryItemsdto> searchInventoryItem(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<InventoryItems> inventoryItemsList = inventoryItemsDAO.search(newValue);
        ArrayList<InventoryItemsdto> inventoryItems = new ArrayList<>();
        for (InventoryItems inventoryItem : inventoryItemsList) {
            InventoryItemsdto dto = new InventoryItemsdto();
            dto.setInventoryItemId(inventoryItem.getInventoryItemId());
            dto.setInventoryName(inventoryItem.getInventoryName());
            dto.setInventoryDescription(inventoryItem.getInventoryDescription());
            dto.setInventoryQuantity(inventoryItem.getInventoryQuantity());
            dto.setInventoryUnit(inventoryItem.getInventoryUnit());

            inventoryItems.add(dto);
        }
        return inventoryItems;

    }
}
