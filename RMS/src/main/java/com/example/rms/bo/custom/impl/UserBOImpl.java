package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.UserBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.UserDAO;
import com.example.rms.dto.Userdto;
import com.example.rms.entity.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserBOImpl implements UserBO {

    UserDAO userDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.USER);

    @Override
    public String getNextUserId() throws SQLException, ClassNotFoundException {
        return userDAO.getNextId();
    }

    @Override
    public boolean saveUser(Userdto dto) throws SQLException, ClassNotFoundException {
        User user = new User(dto.getId(), dto.getName(), dto.getPassword(), dto.getLoginTime(), dto.getEmployeeId());
        return userDAO.save(user);
    }

    @Override
    public ArrayList<Userdto> getAllUsers() throws SQLException, ClassNotFoundException {
        ArrayList<User> userList = userDAO.getAll();
        ArrayList<Userdto> userDtos = new ArrayList<>();

        for (User user : userList) {
            userDtos.add(new Userdto(user.getId(), user.getName(), user.getPassword(), user.getLoginTime(), user.getEmployeeId()));
        }
        return userDtos;
    }

    @Override
    public boolean updateUser(Userdto dto) throws SQLException, ClassNotFoundException {
        User user = new User(dto.getId(), dto.getName(), dto.getPassword(), dto.getLoginTime(), dto.getEmployeeId());
        return userDAO.update(user);
    }

    @Override
    public boolean deleteUser(String userId) throws SQLException, ClassNotFoundException {
        userDAO.delete(userId);
        return true;
    }

//    @Override
//    public Userdto searchUser(String userId) throws SQLException, ClassNotFoundException {
//        User user = userDAO.search(userId);
//        if (user != null) {
//            return new Userdto(user.getId(), user.getName(), user.getPassword(), user.getLoginTime(), user.getEmployeeId());
//        }
//        return null;
//    }

//    public String getUserName(String username) throws SQLException, ClassNotFoundException {
//        return userDAO.getUserName(username);
//    }
//
//    public String getUserId(String isUserName) throws SQLException, ClassNotFoundException {
//        return userDAO.getUserId(isUserName);
//    }
//
//    public String getPassword(String userName) throws SQLException, ClassNotFoundException {
//        return userDAO.getPassword(userName);
//    }
}