package com.example.demo.repositories.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.demo.entities.Employee;

public class EmployeeMapper implements RowMapper<Employee>{
    public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
        Employee employee = new Employee();
        employee.setId(rs.getString("EMP_ID"));
        employee.setName(rs.getString("NAME"));
        employee.setEmail(rs.getString("EMAIL"));
        employee.setFloorSeatSeq(rs.getString("FLOOR_SEAT_SEQ"));
        return employee;
    }
}
