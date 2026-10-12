package com.daviddai.retail.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public final class DataSourceFactory {

    private static final Logger log = LoggerFactory.getLogger(DataSourceFactory.class);

    private DataSourceFactory() {
    }

    public static DataSource create() {
        HikariConfig hc = new HikariConfig();
        hc.setJdbcUrl("jdbc:postgresql://localhost:5432/retail");
        hc.setUsername("pengunin");
        hc.setPassword("pengunin123@");
        hc.setDriverClassName("org.postgresql.Driver");

        hc.setMaximumPoolSize(10);
        hc.setMinimumIdle(2);
        hc.setConnectionTimeout(10000);
        hc.setIdleTimeout(600000);
        hc.setMaxLifetime(1800000);
        hc.setPoolName("RetailHikariPool");

        hc.addDataSourceProperty("cachePrepStmts", "true");
        hc.addDataSourceProperty("prepStmtCacheSize", "250");
        hc.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        log.info("Khởi tạo HikariCP: url={}, poolSize={}",
                hc.getJdbcUrl(), hc.getMaximumPoolSize());

        return new HikariDataSource(hc);
    }
}
