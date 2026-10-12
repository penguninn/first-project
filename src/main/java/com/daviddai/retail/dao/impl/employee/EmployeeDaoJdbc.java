package com.daviddai.retail.dao.impl.employee;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.daviddai.retail.dao.EmployeeDao;
import com.daviddai.retail.dao.impl.AbstractJdbcDao;
import com.daviddai.retail.exception.DataAccessException;
import com.daviddai.retail.mapper.EmployeeMapper;
import com.daviddai.retail.mapper.RowMapper;
import com.daviddai.retail.model.Employee;

public class EmployeeDaoJdbc extends AbstractJdbcDao implements EmployeeDao {

    private static final RowMapper<Employee> EMPLOYEE_MAPPER = EmployeeMapper::mapRsToEntity;

    public EmployeeDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Employee> findById(int id) {
        return queryOne(EmployeeSql.FIND_BY_ID, EMPLOYEE_MAPPER, id);
    }

    @Override
    public Optional<Employee> findByIdForUpdate(int id) {
        return queryOne(EmployeeSql.FIND_BY_ID_FOR_UPDATE, EMPLOYEE_MAPPER, id);
    }

    @Override
    public Optional<Employee> findByUsername(String username) {
        return queryOne(EmployeeSql.FIND_BY_USERNAME, EMPLOYEE_MAPPER, username);
    }

    @Override
    public List<Employee> findAll() {
        return queryList(EmployeeSql.FIND_ALL, EMPLOYEE_MAPPER);
    }

    @Override
    public List<Employee> findByRole(String role) {
        return queryList(EmployeeSql.FIND_BY_ROLE, EMPLOYEE_MAPPER, role);
    }

    @Override
    public List<Employee> search(String keyword, int page, int size) {
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        int offset = Math.max(0, (page - 1) * size);
        return queryList(EmployeeSql.SEARCH, EMPLOYEE_MAPPER,
                like, like, like, like, size, offset);
    }

    @Override
    public int insert(Employee e) {
        return queryOne(EmployeeSql.INSERT, rs -> rs.getInt("id"),
                e.getCode(), e.getFullName(), e.getPhone(), e.getBirthDate(),
                e.getRole(), e.getGender(), e.getAddress(), e.getUsername(),
                e.getPasswordHash(), e.isActive())
                .orElseThrow(() -> new DataAccessException(
                        "Không nhận được ID nhân viên sau khi thêm"));
    }

    @Override
    public void update(Employee e) {
        update(EmployeeSql.UPDATE,
                e.getCode(), e.getFullName(), e.getPhone(), e.getBirthDate(),
                e.getRole(), e.getGender(), e.getAddress(), e.getUsername(),
                e.getPasswordHash(), e.isActive(), e.getId());
    }

    @Override
    public void delete(int id) {
        update(EmployeeSql.DELETE, id);
    }

    @Override
    public void softDelete(int id) {
        update(EmployeeSql.SOFT_DELETE, id);
    }

    @Override
    public int countSearch(String keyword) {
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        return queryOne(EmployeeSql.COUNT_SEARCH, rs -> rs.getInt(1),
                like, like, like, like)
                .orElse(0);
    }

    @Override
    public boolean existsByCode(String code) {
        return queryOne(EmployeeSql.EXISTS_BY_CODE, rs -> rs.getBoolean(1), code)
                .orElse(false);
    }

    @Override
    public boolean existsByUsername(String username) {
        return queryOne(EmployeeSql.EXISTS_BY_USERNAME, rs -> rs.getBoolean(1), username)
                .orElse(false);
    }

    @Override
    public boolean existsByPhone(String phone) {
        return queryOne(EmployeeSql.EXISTS_BY_PHONE, rs -> rs.getBoolean(1), phone)
                .orElse(false);
    }
}
