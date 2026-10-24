package com.daviddai.retail.app;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddai.retail.config.BcryptPasswordEncoder;
import com.daviddai.retail.config.DataSourceFactory;
import com.daviddai.retail.config.PasswordEncoder;
import com.daviddai.retail.dao.EmployeeDao;
import com.daviddai.retail.dao.ProductDao;
import com.daviddai.retail.dao.impl.employee.EmployeeDaoJdbc;
import com.daviddai.retail.dao.impl.product.ProductDaoJdbc;
import com.daviddai.retail.service.AuthService;
import com.daviddai.retail.service.EmployeeService;
import com.daviddai.retail.service.ProductService;
import com.daviddai.retail.service.impl.AuthServiceImpl;
import com.daviddai.retail.service.impl.EmployeeServiceImpl;
import com.daviddai.retail.service.impl.ProductServiceImpl;

public final class AppContext implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(AppContext.class);
    private static volatile AppContext instance;
    private final DataSource dataSource;
    private final PasswordEncoder encoder;

    private final EmployeeDao employeeDao;
    private final AuthService authService;
    private final EmployeeService employeeService;

    private final ProductDao productDao;
    private final ProductService productService;

    private AppContext() {
        this.dataSource = DataSourceFactory.create();
        this.encoder = new BcryptPasswordEncoder();

        this.employeeDao = new EmployeeDaoJdbc(dataSource);
        this.authService = new AuthServiceImpl(employeeDao, encoder);
        this.employeeService = new EmployeeServiceImpl(employeeDao, encoder);

        this.productDao = new ProductDaoJdbc(dataSource);
        this.productService = new ProductServiceImpl(productDao);
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

    public ProductService getProductService() {
        return productService;
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
