package com.example.rms.model;

import com.example.rms.db.DBConnection;
import com.example.rms.dto.Customerdto;
import com.example.rms.dto.Employeedto;
import com.example.rms.util.CrudUtil;

import java.sql.*;
import java.util.ArrayList;

public class EmployeeModel {

    public static String getNextEmployeeId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "select EmployeeID from employees " +
                "order by EmployeeID desc " +
                "limit  1";

        PreparedStatement pts = connection.prepareStatement(sql);
        ResultSet rst = pts.executeQuery();

        if(rst.next()) {
            String string = rst.getString(1); // C001
            String substring = string.substring(1); // 001
            int lastIdIndex = Integer.parseInt(substring); // 1
            int nextIIndex = lastIdIndex+1; // 2
            String newId = String.format("E%03d", nextIIndex); // C002
            return newId;
        }
        return "E001";
    }

    public static boolean saveEmployee(Employeedto employeeDTO) throws SQLException {
        Boolean execute = CrudUtil.execute(
                "insert into employees values (?,?,?,?,?,?)",
                employeeDTO.getId(),
                employeeDTO.getName(),
                employeeDTO.getPosition(),
                employeeDTO.getPhone(),
                employeeDTO.getEmail(),
                Date.valueOf(String.valueOf(employeeDTO.getHireDate())) // Correctly convert LocalDate to java.sql.Date
        );
        return execute;
    }
    public static ArrayList<Employeedto> getAllEmployee() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from employees");

        ArrayList<Employeedto> employeeDTOS = new ArrayList<>();

        while (rst.next()) {
            Employeedto employeeDTO = new Employeedto(
                    rst.getString(1),  // Emp ID
                    rst.getString(2),  // Name
                    rst.getString(3),  // Position
                    rst.getString(4),  // Phone
                    rst.getString(5),  // Email
                    rst.getDate(6)     // Directly use java.sql.Date without conversion
            );
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    public static boolean updateEmployee(Employeedto employeeDTO) throws SQLException {
        return CrudUtil.execute(
                "update employees set Name=?, Position=?, Phone=?, Email=?, HireDate=? where EmployeeID=?",
                employeeDTO.getName(),
                employeeDTO.getPosition(),
                employeeDTO.getPhone(),
                employeeDTO.getEmail(),
                java.sql.Date.valueOf(String.valueOf(employeeDTO.getHireDate())), // Convert LocalDate to java.sql.Date
                employeeDTO.getId()
        );
    }


    public static boolean deleteEmployee(String employeeID) throws SQLException {
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "DELETE FROM Employees WHERE EmployeeID = ?";
//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, id);
//        return statement.executeUpdate() > 0 ? "Success" : "Fail";
        return CrudUtil.execute("delete from employees where EmployeeID=?",employeeID);
    }

    public static Employeedto searchEmployee(String employeeID) throws Exception{
//        Connection connection = DBConnection.getInstance().getConnection();
//        String sql = "SELECT * FROM Employees WHERE EmployeeID = ?";
        ResultSet rst = CrudUtil.execute("SELECT * FROM Employees WHERE EmployeeID = ?", employeeID); // CrudUtil එකේ execute method එක use කරනවා

//        PreparedStatement statement = connection.prepareStatement(sql);
//        statement.setString(1, employeeID);
//
//        ResultSet rst = statement.executeQuery();
        if (rst.next()) {
            return new Employeedto(
                    rst.getString("EmployeeID"),
                    rst.getString("Name"),
                    rst.getString("Position"),
                    rst.getString("Phone"),
                    rst.getString("Email"),
                    rst.getDate("HireDate")
            );
        }
        return null;
    }

    public boolean save(String employId, String name, String position, String phone, String email, Date date) throws SQLException {
        return CrudUtil.execute("insert into employees values(?,?,?,?,?,?)",employId,name,position,phone,email,date);
    }
}