package com.daviddai.retail.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddai.retail.config.PasswordEncoder;
import com.daviddai.retail.dao.EmployeeDao;
import com.daviddai.retail.exception.BusinessException;
import com.daviddai.retail.exception.NotFoundException;
import com.daviddai.retail.exception.ValidationException;
import com.daviddai.retail.mapper.EmployeeMapper;
import com.daviddai.retail.model.Employee;
import com.daviddai.retail.model.dto.command.EmployeeCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.model.dto.result.PageResult;
import com.daviddai.retail.model.enums.Role;
import com.daviddai.retail.service.EmployeeService;
import com.daviddai.retail.ui.UiUtils;

public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeDao dao;
    private final PasswordEncoder encoder;

    public EmployeeServiceImpl(EmployeeDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    @Override
    public EmployeeResult findById(int id) {
        return EmployeeMapper.mapEntityToResult(dao.findById(id)
                .orElseThrow(() -> NotFoundException.of("Nhân viên", id)));

    }

    @Override
    public List<EmployeeResult> findAll() {
        return dao.findAll().stream()
                .map(EmployeeMapper::mapEntityToResult).toList();
    }

    @Override
    public List<EmployeeResult> findByRole(String role) {
        if (!Role.MANAGER.toString().equals(role) && !Role.STAFF.toString().equals(role)) {
            throw new ValidationException("Role phải là MANAGER hoặc STAFF");
        }
        return dao.findByRole(role).stream()
                .map(EmployeeMapper::mapEntityToResult).toList();
    }

    @Override
    public PageResult<EmployeeResult> search(String keyword, int page, int size) {
        if (page < 1) {
            page = 1;
        }
        if (size < 1 || size > 100) {
            size = 20;
        }
        List<EmployeeResult> items = dao.search(keyword, page, size).stream()
                .map(EmployeeMapper::mapEntityToResult).toList();

        return new PageResult<EmployeeResult>(
                items, page,
                size, items.size());
    }

    @Override
    public int create(EmployeeCommand command) {
        String hash = encoder.encode(command.password());
        if (dao.existsByUsername(command.username())) {
            throw new BusinessException("Tài khoản đã tồn tại: " + command.username());
        }
        Employee e = EmployeeMapper.mapCommandToEntity(command);
        e.setCode(UiUtils.gennerateCodeFromName(e.getUsername()));
        e.setPasswordHash(hash);
        return dao.insert(e);
    }

    @Override
    public void update(int id, EmployeeCommand command) {
        if (dao.existsByUsername(command.username())) {
            throw new BusinessException("Tài khoản đã tồn tại: " + command.username());
        }
        if (dao.existsByPhone(command.phone())) {
            throw new BusinessException("SDT khoản đã tồn tại: " + command.phone());
        }
        Employee e = EmployeeMapper.mapCommandToEntity(command);
        dao.update(e);
    }

    @Override
    public void delete(int id) {
        Employee e = dao.findById(id)
                .orElseThrow(() -> NotFoundException.of("Nhân viên", id));
        if ("MANAGER".equalsIgnoreCase(e.getRole())) {
            throw new BusinessException("Không thể xóa");
        }
        dao.delete(id);
    }
}
