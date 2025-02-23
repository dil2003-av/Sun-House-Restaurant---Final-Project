package com.example.rms.dao;

import com.example.rms.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLUtill {
    public static <T> T execute(String sql, Object... objects) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0;i < objects.length;i++){
            pstm.setObject(i+1,objects[i]);
        }
        if (sql.startsWith("SELECT")){
            return (T) (pstm.executeQuery());
        }else {
            return (T) (Boolean) (pstm.executeUpdate()>0);
        }
    }
}
