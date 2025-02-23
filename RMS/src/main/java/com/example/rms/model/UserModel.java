package com.example.rms.model;

import com.example.rms.dto.Userdto;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserModel {

    // Get the next User ID
    public static String getNextUserId() throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT UserID FROM users ORDER BY UserID DESC LIMIT 1");

        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("U%03d", nextId);
        }
        return "U001";
    }
//
//    // Save a User
//    public static boolean saveUser(Userdto user) throws SQLException {
//        return CrudUtil.execute(
//                "INSERT INTO users (UserID, Username, Password, LoginTime, EmployeeID) VALUES (?, ?, ?, ?, ?)",
//                user.getId(),
//                user.getName(),
//                user.getPassword(),
//                user.getLoginTime(),
//                user.getEmployeeId()
//        );
//    }
//
//    // Retrieve all Users
//    public static ArrayList<Userdto> getAllUsers() throws SQLException {
//        ResultSet rst = CrudUtil.execute("SELECT * FROM users");
//        ArrayList<Userdto> userList = new ArrayList<>();
//
//        while (rst.next()) {
//            userList.add(new Userdto(
//                    rst.getString("UserID"),
//                    rst.getString("Username"),
//                    rst.getString("Password"),
//                    rst.getTimestamp("LoginTime").toLocalDateTime(),
//                    rst.getString("EmployeeID")
//            ));
//        }
//        return userList;
//    }
//
//    // Update a User
//    public static boolean updateUser(Userdto user) throws SQLException {
//        return CrudUtil.execute(
//                "UPDATE users SET Username=?, Password=?, LoginTime=?, EmployeeID=? WHERE UserID=?",
//                user.getName(),
//                user.getPassword(),
//                user.getLoginTime(),
//                user.getEmployeeId(),
//                user.getId()
//        );
//    }
//
//    // Delete a User
//    public static boolean deleteUser(String userId) throws SQLException {
//        return CrudUtil.execute("DELETE FROM users WHERE UserID=?", userId);
//    }

    // Search for a User by ID
    public static Userdto searchUser(String userId) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM users WHERE UserID=?", userId);

        if (rst.next()) {
            return new Userdto(
                    rst.getString("UserID"),
                    rst.getString("Username"),
                    rst.getString("Password"),
                    rst.getTimestamp("LoginTime").toLocalDateTime(),
                    rst.getString("EmployeeID")
            );
        }
        return null;
    }

    public boolean save(String userid, String name, String password, String employeeId) throws SQLException {
        return CrudUtil.execute("insert into users(UserID,Username,Password,LoginTime,EmployeeID) values(?,?,?,Now(),?)", userid, name,  password,employeeId );
    }

    public String getUserName(String username) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Username FROM users WHERE Username=?", username);
        String name = null;
        if (rst.next()) {
            name = rst.getString(1);
        }
        return name;
    }

    public String getUserId(String isUserName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT UserID FROM users WHERE Username=?", isUserName);
        String id = null;
        if (rst.next()) {
            id = rst.getString(1);
        }
        return id;
    }

    public String getPassword(String userName) throws SQLException {
        ResultSet rst = CrudUtil.execute("SELECT Password FROM users WHERE UserID=?", userName);
        String id = null;
        if (rst.next()) {
            id = rst.getString(1);
        }
        return id;
    }

}
