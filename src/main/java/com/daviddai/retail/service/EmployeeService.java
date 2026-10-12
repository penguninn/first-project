package com.daviddai.retail.service;

import java.util.List;

import com.daviddai.retail.model.dto.command.EmployeeCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.model.dto.result.PageResult;


public interface EmployeeService {

    EmployeeResult findById(int id);

    List<EmployeeResult> findAll();

    List<EmployeeResult> findByRole(String role);

    PageResult<EmployeeResult> search(String keyword, int page, int pageSize);

    int create(EmployeeCommand command);

    void update(int id, EmployeeCommand command);

    void delete(int id);
}
