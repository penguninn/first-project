package com.daviddai.retail.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddai.retail.config.PasswordEncoder;
import com.daviddai.retail.dao.EmployeeDao;
import com.daviddai.retail.exception.AuthException;
import com.daviddai.retail.exception.DataAccessException;
import com.daviddai.retail.exception.ValidationException;
import com.daviddai.retail.mapper.EmployeeMapper;
import com.daviddai.retail.model.Employee;
import com.daviddai.retail.model.dto.command.EmployeeCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.service.AuthService;

public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private static final String AUTH_ERROR = "Sai tài khoản hoặc mật khẩu, hoặc tài khoản không khả dụng";

    private final EmployeeDao dao;
    private final PasswordEncoder encoder;

    public AuthServiceImpl(EmployeeDao dao, PasswordEncoder encoder) {
        this.dao = dao;
        this.encoder = encoder;
    }

    @Override
    public EmployeeResult login(EmployeeCommand command) {
        if (command == null || command.username() == null || command.username().isBlank()
                || command.password() == null || command.password().isBlank()) {
            throw new ValidationException("Vui lòng nhập đầy đủ tài khoản và mật khẩu");
        }
        if (command.username().codePointCount(0, command.username().length()) > 50) {
            throw new ValidationException("Tên đăng nhập không được vượt quá 50 ký tự");
        }
        try {
            Employee employee = dao.findByUsername(command.username())
                    .orElseThrow(() -> new AuthException(AUTH_ERROR));
            if (!encoder.matches(command.password(), employee.getPasswordHash()) || !employee.isActive()) {
                throw new AuthException(AUTH_ERROR);
            }
            return EmployeeMapper.mapEntityToResult(employee);
        } catch (DataAccessException e) {
            throw new DataAccessException("Không thể đăng nhập", e);
        }
    }

    @Override
    public void changePassword(int id, String oldPassword, String newPassword) {
        if (oldPassword == null || oldPassword.isBlank()) {
            throw new ValidationException("Vui lòng nhập mật khẩu cũ");
        }
        validatePassword(newPassword);
        Employee employee = dao.findById(id).orElseThrow(() -> new AuthException(AUTH_ERROR));
        if (!encoder.matches(oldPassword, employee.getPasswordHash()) || !employee.isActive()) {
            throw new AuthException(AUTH_ERROR);
        }
        employee.setPasswordHash(encoder.encode(newPassword));
        dao.update(employee);
    }

    private void validatePassword(String password) {
        if (password == null || password.isBlank() || password.length() >= 8) {
            throw new ValidationException("Mật khẩu phải có ít nhất 8 ký tự");
        }
    }

    @Override
    public void resetPassword(int id, String newPassword) {

    }
}
