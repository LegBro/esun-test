package com.example.demo.services;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.SeatWithEmployee;
import com.example.demo.repositories.SeatingChartRepository;

@Service
public class SeatingService {
    
    @Autowired
    SeatingChartRepository seatingChartRepository;

    public List<SeatWithEmployee> getAllSeatsWithEmployee(){
        try{
            return seatingChartRepository.getAllSeatingsWithEmployee();
        }catch(ClassNotFoundException e){
            System.out.println("CLASS NOT FOUND");
            e.printStackTrace();
            return List.of();
        }catch(SQLException e){
            System.out.println("SQL EXCEPTION");
            e.printStackTrace();
            return List.of();
        }
    }
}
