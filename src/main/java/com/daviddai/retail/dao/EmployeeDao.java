package com.daviddai.retail.dao;

import java.util.List;
import java.util.Optional;

import com.daviddai.retail.model.Employee;

public interface EmployeeDao {

    Optional<Employee> findById(int id);

    Optional<Employee> findByIdForUpdate(int id);

    Optional<Employee> findByUsername(String username);

    List<Employee> findAll();

    List<Employee> findByRole(String role);

    List<Employee> search(String keyword, int page, int size);

    int countSearch(String keyword);

    boolean existsByCode(String code);

    boolean existsByUsername(String username);

    boolean existsByPhone(String phone);

    int insert(Employee employee);

    void update(Employee employee);

    void delete(int id);

    void softDelete(int id);
}
