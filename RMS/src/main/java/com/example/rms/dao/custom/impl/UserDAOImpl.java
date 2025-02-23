package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.UserDAO;
import com.example.rms.entity.Employee;
import com.example.rms.entity.User;
import com.example.rms.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserDAOImpl implements UserDAO {
    @Override
    public ArrayList<User> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM users");
        ArrayList<User> userList = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString("UserID"),
                    rst.getString("Username"),
                    rst.getString("Password"),
                    rst.getTimestamp("LoginTime").toLocalDateTime(),
                    rst.getString("EmployeeID")
            );
            userList.add(user);
        }
        return userList;
    }



    @Override
    public boolean save(User user) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "INSERT INTO users VALUES (?, ?, ?, ?, ?)",
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getLoginTime(),
                user.getEmployeeId()
        );
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute(
                "UPDATE users SET Username=?, Password=?, LoginTime=?, EmployeeID=? WHERE UserID=?",
                user.getName(),
                user.getPassword(),
                user.getLoginTime(),
                user.getEmployeeId(),
                user.getId()
        );
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT UserID FROM users WHERE UserID=?", id);
        return rst.next();
    }

    @Override
    public void delete(String userId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("DELETE FROM users WHERE UserID=?", userId);
    }

    @Override
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT UserID FROM users ORDER BY UserID DESC LIMIT 1");
        if (rst.next()) {
            String lastId = rst.getString(1).substring(1);
            int nextId = Integer.parseInt(lastId) + 1;
            return String.format("U%03d", nextId);
        }
        return "U001";
    }

    @Override
    public ArrayList<User> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("SELECT * FROM users WHERE UserID=?", newValue);
        ArrayList<User> userList = new ArrayList<>();

        while (rst.next()) {
            User user = new User(
                    rst.getString("UserID"),
                    rst.getString("Username"),
                    rst.getString("Password"),
                    rst.getTimestamp("LoginTime").toLocalDateTime(),
                    rst.getString("EmployeeID")
            );
            userList.add(user);
        }
        return userList;
    }
}
