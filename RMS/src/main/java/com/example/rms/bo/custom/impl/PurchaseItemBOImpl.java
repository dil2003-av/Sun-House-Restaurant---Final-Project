package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.PurchaseItemBO;
//import com.example.rms.bo.custom.PurchaseItemsBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.PurchaseItemDAO;
//import com.example.rms.dao.custom.PurchaseItemsDAO;
import com.example.rms.dto.PurchaseItmsdto;
import com.example.rms.entity.PurchaseItem;
//import com.example.rms.entity.PurchaseItems;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseItemBOImpl implements PurchaseItemBO {

    PurchaseItemDAO purchaseItemsDAO = (PurchaseItemDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.PURCHASE_ITEMS);

    @Override
    public String getNextPurchaseItemId() throws SQLException, ClassNotFoundException {
        return purchaseItemsDAO.getNextId();
    }

    @Override
    public boolean savePurchaseItem(PurchaseItmsdto dto) throws SQLException, ClassNotFoundException {
        PurchaseItem purchaseItem = new PurchaseItem(dto.getPurchaseItemId(), dto.getInventoryItemId(),
                dto.getPurchaseId(), dto.getPurchasePrice(), dto.getQuantity(), dto.getStatus());
        return purchaseItemsDAO.save(purchaseItem);
    }

    @Override
    public ArrayList<PurchaseItmsdto> getAllPurchaseItems() throws SQLException, ClassNotFoundException {
        ArrayList<PurchaseItem> purchaseItemsList = purchaseItemsDAO.getAll();
        ArrayList<PurchaseItmsdto> purchaseItemsDtos = new ArrayList<>();

        for (PurchaseItem purchaseItem : purchaseItemsList) {
            purchaseItemsDtos.add(new PurchaseItmsdto(
                    purchaseItem.getPurchaseItemId(),
                    purchaseItem.getInventoryItemId(),
                    purchaseItem.getPurchaseId(),
                    purchaseItem.getPurchasePrice(),
                    purchaseItem.getQuantity(),
                    purchaseItem.getStatus()
            ));
        }
        return purchaseItemsDtos;
    }

    @Override
    public boolean updatePurchaseItem(PurchaseItmsdto dto) throws SQLException, ClassNotFoundException {
        PurchaseItem purchaseItem = new PurchaseItem(dto.getPurchaseItemId(), dto.getInventoryItemId(),
                dto.getPurchaseId(), dto.getPurchasePrice(), dto.getQuantity(), dto.getStatus());
        return purchaseItemsDAO.update(purchaseItem);
    }

    @Override
    public boolean deletePurchaseItem(String purchaseItemId) throws SQLException, ClassNotFoundException {
        purchaseItemsDAO.delete(purchaseItemId);
        return true;
    }
@Override
public ArrayList<PurchaseItmsdto> searchPurchaseItem(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<PurchaseItem> purchaseItem = purchaseItemsDAO.search(newValue);
        ArrayList<PurchaseItmsdto> purchaseItemsDtos = new ArrayList<>();

//        for (PurchaseItem purchaseItem : purchaseItemsList) {
//            purchaseItemsDtos.add(new PurchaseItmsdto(
//                    purchaseItem.getPurchaseItemId(),
//                    purchaseItem.getInventoryItemId(),
//                    purchaseItem.getPurchaseId(),
//                    purchaseItem.getPurchasePrice(),
//                    purchaseItem.getQuantity(),
//                    purchaseItem.getStatus()
//            ));
//        }
//        return purchaseItemsDtos;
//    }

        for (PurchaseItem purchaseItem1 : purchaseItem) {
            PurchaseItmsdto purchaseItmsdto = new PurchaseItmsdto();
            purchaseItmsdto.setPurchaseItemId(purchaseItem1.getPurchaseItemId());
            purchaseItmsdto.setInventoryItemId(purchaseItem1.getInventoryItemId());
            purchaseItmsdto.setPurchaseId(purchaseItem1.getPurchaseId());
            purchaseItmsdto.setPurchasePrice(purchaseItem1.getPurchasePrice());
            purchaseItmsdto.setQuantity(purchaseItem1.getQuantity());
            purchaseItmsdto.setStatus(purchaseItem1.getStatus());
            purchaseItemsDtos.add(purchaseItmsdto);
        }
        return purchaseItemsDtos;
    }
}
