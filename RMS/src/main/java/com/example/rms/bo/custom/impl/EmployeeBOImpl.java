package com.example.rms.bo.custom.impl;

import com.example.rms.bo.custom.EmployeeBO;
import com.example.rms.dao.DAOFactory;
import com.example.rms.dao.custom.EmployeeDAO;
import com.example.rms.dto.Employeedto;
import com.example.rms.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {

    EmployeeDAO employeeDAO = (EmployeeDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOType.EMPLOYEE);

    @Override
    public String getNextEmployeeId() throws SQLException, ClassNotFoundException {
        return employeeDAO.getNextId();
    }

    @Override
    public ArrayList<Employeedto> getAllEmployee() throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employeeList = employeeDAO.getAll();
        ArrayList<Employeedto> employees = new ArrayList<>();
        for (Employee employee : employeeList) {
            employees.add(new Employeedto(
                    employee.getId(),
                    employee.getName(),
                    employee.getPosition(),
                    employee.getPhone(),
                    employee.getEmail(),
                    employee.getHireDate()
            ));
        }
        return employees;
    }

    @Override
    public boolean saveEmployee(Employeedto dto) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(
                dto.getId(),
                dto.getName(),
                dto.getPosition(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getHireDate()
        );
        return employeeDAO.save(employee);
    }

//    @Override
//    public ArrayList<Employeedto> getAllEmployees() throws SQLException, ClassNotFoundException {
//        ArrayList<Employee> employeeList = employeeDAO.getAll();
//        ArrayList<Employeedto> employees = new ArrayList<>();
//        for (Employee employee : employeeList) {
//            employees.add(new Employeedto(
//                    employee.getId(),
//                    employee.getName(),
//                    employee.getPosition(),
//                    employee.getPhone(),
//                    employee.getEmail(),
//                    employee.getHireDate()
//            ));
//        }
//        return employees;
//    }

    @Override
    public boolean updateEmployee(Employeedto dto) throws SQLException, ClassNotFoundException {
        Employee employee = new Employee(
                dto.getId(),
                dto.getName(),
                dto.getPosition(),
                dto.getPhone(),
                dto.getEmail(),
                dto.getHireDate()
        );
        return employeeDAO.update(employee);
    }

    @Override
    public boolean deleteEmployee(String employeeId) throws SQLException, ClassNotFoundException {
        employeeDAO.delete(employeeId);
        return true;
    }

    public ArrayList<Employeedto> searchEmployee(String newValue) throws SQLException, ClassNotFoundException {
        ArrayList<Employee> employeeList = employeeDAO.search(newValue);
        ArrayList<Employeedto> employees = new ArrayList<>();
        for (Employee employee : employeeList) {
            employees.add(new Employeedto(
                    employee.getId(),
                    employee.getName(),
                    employee.getPosition(),
                    employee.getPhone(),
                    employee.getEmail(),
                    employee.getHireDate()
            ));
        }
        return employees;
    }
}
