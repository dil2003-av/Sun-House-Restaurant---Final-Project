package com.example.rms.dao.custom.impl;

import com.example.rms.dao.SQLUtill;
import com.example.rms.dao.custom.EmployeeDAO;
import com.example.rms.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {

    public boolean save(Employee employeeEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("insert into employees values (?,?,?,?,?,?)",
                employeeEntity.getId(), employeeEntity.getName(), employeeEntity.getPosition(),employeeEntity.getPhone(), employeeEntity.getEmail() ,employeeEntity.getHireDate());
    }

    public void  delete(String employeeId) throws SQLException, ClassNotFoundException {
        SQLUtill.execute("delete from employees where EmployeeID=? = ?", employeeId);

    }

    public ArrayList<Employee> search(String newValue) throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("SELECT * FROM Employees WHERE EmployeeID = ?", newValue);
        ArrayList<Employee> employeeDTOS = new ArrayList<>();
        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString("id"),
                    rst.getString("name"),
                    rst.getString("position"),
                    rst.getString("phone"),
                    rst.getString("email"),
                    rst.getDate("hireDateStr")

            );
            employeeDTOS.add(employee);
        }
        return employeeDTOS;
    }

    public ArrayList<Employee> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("select * from employees");
        ArrayList<Employee> employees = new ArrayList<>();
        while (rst.next()) {
            Employee employee = new Employee(
                    rst.getString("Employee ID"),  // Emp ID
                    rst.getString("Name"),  // Name
                    rst.getString("Position"),  // Position
                    rst.getString("Phone"),  // Phone
                    rst.getString("Email"),  // Email
                    rst.getDate("hireDate")
            );
            employees.add(employee);
        }
        return employees;
    }

    public boolean update(Employee employeeEntity) throws SQLException, ClassNotFoundException {
        return SQLUtill.execute("update employees set Name=?, Position=?, Phone=?, Email=?, HireDate=? where EmployeeID=?",
                employeeEntity.getName(), employeeEntity.getPosition(), employeeEntity.getPhone(), employeeEntity.getEmail(),employeeEntity.getHireDate(), employeeEntity.getId());
    }

    @Override
    public boolean existId(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtill.execute("select EmployeeID from employees order by EmployeeID desc limit  1" );
        if (rst.next()) {
            return rst.getString(1);
        }
        return null;
    }
}

