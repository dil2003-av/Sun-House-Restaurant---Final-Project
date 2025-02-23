package com.example.rms.bo.custom;

import com.example.rms.bo.SuperBo;
import com.example.rms.dto.Userdto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserBO extends SuperBo {
    String getNextUserId() throws SQLException, ClassNotFoundException;

    boolean saveUser(Userdto dto) throws SQLException, ClassNotFoundException;

    ArrayList<Userdto> getAllUsers() throws SQLException, ClassNotFoundException;

    boolean updateUser(Userdto dto) throws SQLException, ClassNotFoundException;

    boolean deleteUser(String userId) throws SQLException, ClassNotFoundException;

   // Userdto searchUser(String userId) throws SQLException, ClassNotFoundException;
}
