package com.example.rms.model;

import com.example.rms.Tm.MenuTm;
import com.example.rms.db.DBConnection;
import com.example.rms.dto.Menudto;
import com.example.rms.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class MenuModel {

    public static String getNextMenuItemId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT MenuItemID FROM menuitems ORDER BY MenuItemID DESC LIMIT 1";
        PreparedStatement pts = connection.prepareStatement(sql);
        ResultSet rst = pts.executeQuery();

        if (rst.next()) {
            String string = rst.getString(1);
            String substring = string.substring(1);
            int lastIdIndex = Integer.parseInt(substring);
            int nextIndex = lastIdIndex + 1;
            return String.format("M%03d", nextIndex);
        }
        return "M001";
    }

    public static boolean saveMenuItem(Menudto menuDto) throws SQLException {
        return CrudUtil.execute(
                "INSERT INTO menuitems VALUES (?,?,?,?,?,?)",
                menuDto.getMenuid(),
                menuDto.getMenuname(),
                menuDto.getMenudesc(),
                menuDto.getPrice(),
                menuDto.getCategory(),
                menuDto.getKitchensection()
        );
    }

    public static ArrayList<Menudto> getAllMenuItems() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM menuitems");

        ArrayList<Menudto> menuItems = new ArrayList<>();
        while (rst.next()) {
            menuItems.add(new Menudto(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getString(5),
                    rst.getString(6)
            ));
        }
        return menuItems;
    }

    public static boolean updateMenuItem(Menudto menuDto) throws SQLException {
        return CrudUtil.execute(
                "UPDATE menuitems SET Name=?, Description=?, Price=?, Category=?, KitchenSection=? WHERE MenuItemID=?",
                menuDto.getMenuname(),
                menuDto.getMenudesc(),
                menuDto.getPrice(),
                menuDto.getCategory(),
                menuDto.getKitchensection(),
                menuDto.getMenuid()
        );
    }

    public static boolean deleteMenuItem(String menuId) throws SQLException {
        return CrudUtil.execute("DELETE FROM menuitems WHERE MenuItemID=?", menuId);
    }


    public static Menudto searchMenuItem(String menuItemID) throws Exception {
        ResultSet rst = CrudUtil.execute("select * from menuitems where MenuItemID=?", menuItemID);

        if (rst.next()) {
            return new Menudto(
                    rst.getString("MenuItemID"),
                    rst.getString("Name"),
                    rst.getString("Description"),
                    rst.getDouble("Price"),
                    rst.getString("Category"),
                    rst.getString("KitchenSection")
            );
        }
        return null;
    }

//    public static Menudto searchMenuItemName(String menuItemName) {
//
//    }
}

