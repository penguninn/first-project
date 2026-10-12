package com.daviddai.retail.app;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddai.retail.config.BcryptPasswordEncoder;
import com.daviddai.retail.config.DataSourceFactory;
import com.daviddai.retail.config.PasswordEncoder;
import com.daviddai.retail.dao.EmployeeDao;
import com.daviddai.retail.dao.impl.employee.EmployeeDaoJdbc;
import com.daviddai.retail.service.AuthService;
import com.daviddai.retail.service.EmployeeService;
import com.daviddai.retail.service.impl.AuthServiceImpl;
import com.daviddai.retail.service.impl.EmployeeServiceImpl;

public final class AppContext implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(AppContext.class);
    private static volatile AppContext instance;
    private final DataSource dataSource;
    private final PasswordEncoder encoder;
    private final EmployeeDao employeeDao;
    private final EmployeeService employeeService;
    private final AuthService authService;

    private AppContext() {
        this.dataSource = DataSourceFactory.create();
        this.employeeDao = new EmployeeDaoJdbc(dataSource);
        this.encoder = new BcryptPasswordEncoder();
        this.authService = new AuthServiceImpl(employeeDao, encoder);
        this.employeeService = new EmployeeServiceImpl(employeeDao, encoder);
    }

    public static AppContext getInstance() {
        if (instance == null) {
            synchronized (AppContext.class) {
                if (instance == null) {
                    instance = new AppContext();
                }
            }
        }
        return instance;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public PasswordEncoder getPasswordEncoder() {
        return encoder;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public AuthService getAuthService() {
        return authService;
    }

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    @Override
    public void close() {
        if (dataSource instanceof AutoCloseable ac) {
            try {
                ac.close();
            } catch (Exception e) {
                log.warn("Lỗi khi đóng DataSource", e);
            }
        }
    }

}
