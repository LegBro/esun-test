package com.example.demo.repositories;

import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Employee;
import com.example.demo.repositories.mappers.EmployeeMapper;

@Repository
public class EmployeeRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Employee> getAllEmployees() throws ClassNotFoundException,SQLException{
        MapSqlParameterSource params = new MapSqlParameterSource();
        Map<String, Object> result = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("get_all_employees")
            .withoutProcedureColumnMetaDataAccess()
            .returningResultSet("#result-set-1", new EmployeeMapper())
            .execute(params);

        return (List<Employee>) result.get("#result-set-1");

    }

    public int updateSeatOfEmployee(String employeeId, String seatId) throws SQLException{
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("employeeId", employeeId)
            .addValue("floorSeatSeq", seatId);
        Map<String, Object> result = new SimpleJdbcCall(jdbcTemplate)
            .withProcedureName("set_seat_of_employee")
            .declareParameters(
                new SqlParameter("employeeId", Types.CHAR),
                new SqlParameter("floorSeatSeq", Types.VARCHAR),
                new SqlOutParameter("rowsAffected", Types.INTEGER)
            )
            .withoutProcedureColumnMetaDataAccess()
            .execute(params);
        return (int) result.get("rowsAffected");
    }
}
