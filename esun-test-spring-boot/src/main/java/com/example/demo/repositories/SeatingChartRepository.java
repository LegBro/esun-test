package com.example.demo.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.demo.constants.StoreProcedureNames;
import com.example.demo.entities.SeatWithEmployee;
import com.example.demo.repositories.mappers.SeatWithEmployeeMapper;

@Repository
public class SeatingChartRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<SeatWithEmployee> getAllSeatingsWithEmployee() throws ClassNotFoundException,SQLException{
        MapSqlParameterSource params = new MapSqlParameterSource();
        Map<String, Object> result = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName(StoreProcedureNames.SEATINGCHART_GET_ALL_SEATINGS_WITH_EMPLOYEE)
            .withoutProcedureColumnMetaDataAccess()
            .returningResultSet("SeatsWithEmployee", new SeatWithEmployeeMapper())
            .execute(params);
        return (List<SeatWithEmployee>) result.get("SeatsWithEmployee"); 
    }
}
