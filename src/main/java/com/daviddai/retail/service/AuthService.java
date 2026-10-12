package com.daviddai.retail.service;

import com.daviddai.retail.model.dto.command.EmployeeCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;

public interface AuthService {

    EmployeeResult login(EmployeeCommand command);

    void changePassword(int id, String oldPassword, String newPassword);

    void resetPassword(int id, String newPassword);
}
