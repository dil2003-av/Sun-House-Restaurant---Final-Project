package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.MenuDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.Menu;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MenuDAOImpl implements MenuDAO {

    @Override
    public ArrayList<Menu> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM menuitems");

        ArrayList<Menu> menuItems = new ArrayList<>();
        while (rst.next()) {
//            menuItems.add(new Menudto(
//                    rst.getString(1),
//                    rst.getString(2),
//                    rst.getString(3),
//                    rst.getDouble(4),
//                    rst.getString(5),
//                    rst.getString(6)
//            ));
            Menu menu = new Menu(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6)
            );
                   menuItems.add(menu);

        }
        return menuItems;
    }

    @Override
    public boolean save(Menu Dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "INSERT INTO menuitems VALUES (?,?,?,?,?,?)",
//                menuDto.getMenuid(),
//                menuDto.getMenuname(),
//                menuDto.getMenudesc(),
//                menuDto.getPrice(),
//                menuDto.getCategory(),
//                menuDto.getKitchensection()
//        );
        return SQLUtill.execute("INSERT INTO menuitems VALUES (?,?,?,?,?,?)");
    }

    @Override
    public boolean update(Menu Dto) throws SQLException, ClassNotFoundException {
//        return CrudUtil.execute(
//                "UPDATE menuitems SET Name=?, Description=?, Price=?, Category=?, KitchenSection=? WHERE MenuItemID=?",
//                menuDto.getMenuname(),
//                menuDto.getMenudesc(),
//                menuDto.getPrice(),
//                menuDto.getCategory(),
//                menuDto.getKitchensection(),
//                menuDto.getMenuid()
//        );
        return SQLUtill.execute("UPDATE menuitems SET Name=?, Description=?, Price=?, Category=?, KitchenSection=? WHERE MenuItemID=?");
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public void delete(String menuId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM menuitems WHERE MenuItemID=?", menuId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
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
        ResultSet rst = SQLUtill.execute("SELECT MenuItemID FROM menuitems ORDER BY MenuItemID DESC LIMIT 1");
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<Menu> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("select * from menuitems where MenuItemID=?", newValue);

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
//    }
     ArrayList<Menu> menuItems = new ArrayList<>();
     while (rst.next()) {
         Menu menu = new Menu(
                 rst.getString("MenuItemID"),
                   rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getDouble("Price"),
                    rst.getString("Category"),
                    rst.getString("KitchenSection")
         );
         menuItems.add(menu);
     }
     return menuItems;
    }
}



