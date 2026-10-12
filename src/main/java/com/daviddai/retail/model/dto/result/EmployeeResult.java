package com.daviddai.retail.model.dto.result;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EmployeeResult(Integer id, String code, String fullName, String phone,
        LocalDate birthDate, String role, Boolean gender, String address,
        String username, LocalDateTime createdAt, boolean active) {
}
