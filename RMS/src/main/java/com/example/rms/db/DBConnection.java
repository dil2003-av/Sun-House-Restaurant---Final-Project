package com.example.rms.db;

import lombok.Getter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter

public class DBConnection {
    private static DBConnection dbConnection;
    private Connection connection;
    private String URL = "jdbc:mysql://localhost:3306/RMS";

    private String USER = "root";

    private String PASSWORD = "Ijse@1234";

    private DBConnection() throws SQLException {
        connection = DriverManager.getConnection(URL,USER,PASSWORD);
    }
    public static DBConnection getInstance() throws SQLException {
        if (dbConnection == null) {
            dbConnection = new DBConnection();
        }
        return dbConnection;
    }


}
