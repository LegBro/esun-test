package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ApiConsts;
import com.example.demo.entities.Employee;
import com.example.demo.fromJson.UpdateSeatModel;
import com.example.demo.services.EmployeeService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(ApiConsts.API_PREFIX)
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping(ApiConsts.CONTROLLER_EMPLOYEE + "/getAllEmployees")
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @PostMapping(ApiConsts.CONTROLLER_EMPLOYEE + "/setSeatOfEmployee")
    public int setSeatOfEmployee(@RequestBody UpdateSeatModel updateSeatModel) {
        return employeeService.updateSeatOfEmployee(updateSeatModel);
    }
    
}
