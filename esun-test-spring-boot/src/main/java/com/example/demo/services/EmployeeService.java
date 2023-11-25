package com.example.demo.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.EmployeeRepository;

@Service
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> getAllEmployees(){
      try {
        return employeeRepository.getAllEmployees();
      } catch (ClassNotFoundException e) {
        System.out.println("CLASS NOT FOUND");
        e.printStackTrace();
        return List.of();
      } catch (SQLException e) {
        System.out.println("SQL EXCEPTION");
        e.printStackTrace();
        return List.of();
      }
    };
}
