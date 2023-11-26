package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.entities.Employee;
import com.example.demo.entities.SeatWithEmployee;

public class SeatWithEmployeeMapper implements RowMapper<SeatWithEmployee>{
    public SeatWithEmployee mapRow(ResultSet rs, int rowNum) throws SQLException {
        SeatWithEmployee seatWithEmployee = new SeatWithEmployee();
        seatWithEmployee.setFloorSeatSeq(rs.getString("FLOOR_SEAT_SEQ"));
        seatWithEmployee.setFloorNo(rs.getInt("FLOOR_NO"));
        seatWithEmployee.setSeatNo(rs.getInt("SEAT_NO"));
        if(rs.getString("EMP_ID") != null){
            Employee employee = new Employee();
            employee.setId(rs.getString("EMP_ID"));
            employee.setName(rs.getString("NAME"));
            employee.setEmail(rs.getString("EMAIL"));
            employee.setFloorSeatSeq(rs.getString("FLOOR_SEAT_SEQ"));
            seatWithEmployee.setEmployee(employee);
        }
        
        return seatWithEmployee;
    }
}
