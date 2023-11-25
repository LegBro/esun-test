package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.SeatWithEmployee;
import com.example.demo.services.SeatingService;

@RestController
public class SeatingChartController {
    
    @Autowired
    SeatingService seatingService;

    @GetMapping("/api/Seating/getAllSeatsWithEmployee")
    public List<SeatWithEmployee> getAllEmployees(){
        return seatingService.getAllSeatsWithEmployee();
    }

}
