package com.daviddai.retail.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.daviddai.retail.model.Employee;
import com.daviddai.retail.model.dto.command.EmployeeCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;

public final class EmployeeMapper {

    private EmployeeMapper() {
    }

    public static Employee mapRsToEntity(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setCode(rs.getString("code"));
        e.setFullName(rs.getString("full_name"));
        e.setPhone(rs.getString("phone"));
        e.setBirthDate(rs.getObject("birth_date", LocalDate.class));
        e.setRole(rs.getString("role"));
        e.setGender(rs.getObject("gender", Boolean.class));
        e.setAddress(rs.getString("address"));
        e.setUsername(rs.getString("username"));
        e.setPasswordHash(rs.getString("password_hash"));
        e.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        e.setActive(rs.getBoolean("is_active"));
        return e;
    }

    public static EmployeeResult mapEntityToResult(Employee e) {
        return new EmployeeResult(
                e.getId(),
                e.getCode(),
                e.getFullName(),
                e.getPhone(),
                e.getBirthDate(),
                e.getRole(),
                e.getGender(),
                e.getAddress(),
                e.getUsername(),
                e.getCreatedAt(),
                e.isActive());
    }

    public static Employee mapCommandToEntity(EmployeeCommand command) {
        Employee e = new Employee();
        e.setFullName(command.fullName() != null ? command.fullName().trim() : null);
        e.setPhone(command.phone());
        e.setBirthDate(command.birthDate());
        e.setRole(command.role() != null && !command.role().isBlank() ? command.role() : "STAFF");
        e.setGender(command.gender());
        e.setAddress(command.address());
        e.setUsername(command.username());
        e.setActive(command.active());
        return e;
    }
}
