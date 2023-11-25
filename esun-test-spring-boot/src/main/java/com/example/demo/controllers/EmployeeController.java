package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Employee;
import com.example.demo.fromJson.UpdateSeatModel;
import com.example.demo.services.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/api/Employee/getAllEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping("/api/Employee/setSeatOfEmployee")
    public int postMethodName(@RequestBody UpdateSeatModel updateSeatModel) {
        return employeeService.updateSeatOfEmployee(updateSeatModel);
    }
    
}
