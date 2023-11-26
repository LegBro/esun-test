package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.constants.ApiConsts;
import com.example.demo.entities.SeatWithEmployee;
import com.example.demo.services.SeatingService;

@RestController
@RequestMapping(ApiConsts.API_PREFIX)
public class SeatingChartController {
    
    @Autowired
    SeatingService seatingService;

    @GetMapping(ApiConsts.CONTROLLER_SEATINGCHART + "/getAllSeatsWithEmployee")
    public List<SeatWithEmployee> getAllEmployees(){
        return seatingService.getAllSeatsWithEmployee();
    }

}
