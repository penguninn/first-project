package com.daviddai.retail.model.dto.command;

import java.time.LocalDate;

public record EmployeeCommand(
        String fullName,
        String phone,
        LocalDate birthDate,
        String role,
        Boolean gender,
        String address,
        String username,
        String password,
        boolean active) {
}
