package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.MenuBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.MenuDAO;
import com.example.rms.dto.Menudto;
import com.example.rms.entity.Menu;

import java.sql.SQLException;
import java.util.ArrayList;

public class MenuBOImpl implements MenuBO {

    MenuDAO menuDAO = (MenuDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.MENU);

    @Override
    public String getNextMenuItemId() throws SQLException, ClassNotFoundException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "SELECT MenuItemID FROM menuitems ORDER BY MenuItemID DESC LIMIT 1";
//        PreparedStatement pts = connection.prepareStatement(sql);
//        ResultSet rst = pts.executeQuery();
//
//        if (rst.next()) {
//            String string = rst.getString(1);
//            String substring = string.substring(1);
//            int lastIdIndex = Integer.parseInt(substring);
//            int nextIndex = lastIdIndex + 1;
//            return String.format("M%03d", nextIndex);
//        }
//        return "M001";
        return menuDAO.getNextId();
    }
@Override
public boolean saveMenuItem(Menudto menuDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "INSERT INTO menuitems VALUES (?,?,?,?,?,?)",
//                menuDto.getMenuid(),
//                menuDto.getMenuname(),
//                menuDto.getMenudesc(),
//                menuDto.getPrice(),
//                menuDto.getCategory(),
//                menuDto.getKitchensection()
//        );
        Menu menu = new Menu(menuDto.getMenuid(),menuDto.getMenuname(),menuDto.getMenudesc(),menuDto.getPrice(),menuDto.getCategory(),menuDto.getKitchensection());
        return menuDAO.save(menu);
    }

    @Override
    public ArrayList<Menudto> getAllMenuItems() throws SQLException, ClassNotFoundException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM menuitems");
//
//        ArrayList<Menudto> menuItems = new ArrayList<>();
//        while (rst.next()) {
//            menuItems.add(new Menudto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(4),
//                    rst.getString(5),
//                    rst.getString(6)
//            ));
//        }
//        return menuItems;
        ArrayList<Menu> menus = menuDAO.getAll();
        ArrayList<Menudto> menuList = new ArrayList<>();
        for (Menu menu : menus) {
            Menudto menudto = new Menudto();
            menudto.setMenuid(menu.getMenuid());
            menudto.setMenuname(menu.getMenuname());
            menudto.setMenudesc(menu.getMenudesc());
            menudto.setPrice(menu.getPrice());
            menudto.setCategory(menu.getCategory());
            menudto.setKitchensection(menu.getKitchensection());
            menuList.add(menudto);
        }
        return menuList;

    }

@Override
public boolean updateMenuItem(Menudto menuDto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE menuitems SET Name=?, Description=?, Price=?, Category=?, KitchenSection=? WHERE MenuItemID=?",
//                menuDto.getMenuname(),
//                menuDto.getMenudesc(),
//                menuDto.getPrice(),
//                menuDto.getCategory(),
//                menuDto.getKitchensection(),
//                menuDto.getMenuid()
//        );
        Menu menu = new Menu(menuDto.getMenuid(),menuDto.getMenuname(),menuDto.getMenudesc(),menuDto.getPrice(),menuDto.getCategory(),menuDto.getKitchensection());
        return menuDAO.update(menu);
    }
@Override
public boolean deleteMenuItem(String menuId) throws SQLException, ClassNotFoundException {
        //return CrudUtil.execute("DELETE FROM menuitems WHERE MenuItemID=?", menuId);
         menuDAO.delete(menuId);
    return false;
}

@Override
public ArrayList<Menudto> searchMenuItem(String newValue) throws Exception {
//        ResultSet rst = CrudUtil.execute("select * from menuitems where MenuItemID=?", menuItemID);
//
//        if (rst.next()) {
//            return new Menudto(
//                    rst.getString("MenuItemID"),
//                    rst.getString("Name"),
//                    rst.getString("Description"),
//                    rst.getDouble("Price"),
//                    rst.getString("Category"),
//                    rst.getString("KitchenSection")
//            );
//        }
//        return null;

        ArrayList<Menu> menus = menuDAO.search(newValue);
        ArrayList<Menudto> menuList = new ArrayList<>();
        for (Menu menu : menus) {
            Menudto menudto = new Menudto();
            menudto.setMenuid(menu.getMenuid());
            menudto.setMenuname(menu.getMenuname());
            menudto.setMenudesc(menu.getMenudesc());
            menudto.setPrice(menu.getPrice());
            menudto.setCategory(menu.getCategory());
            menudto.setKitchensection(menu.getKitchensection());
            menuList.add(menudto);
        }
        return menuList;

    }
}
