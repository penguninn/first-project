package com.daviddai.retail.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.daviddai.retail.exception.DataAccessException;
import com.daviddai.retail.mapper.RowMapper;

public abstract class AbstractJdbcDao {

    protected final DataSource dataSource;
    private final ThreadLocal<Connection> transactionConnection = new ThreadLocal<>();

    protected AbstractJdbcDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected <T> Optional<T> queryOne(String sql, RowMapper<T> mapper, Object... params) {
        return withConnection(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindParams(ps, params);
                try (ResultSet rs = ps.executeQuery()) {
                    return rs.next() ? Optional.of(mapper.map(rs)) : Optional.empty();
                }
            }
        });
    }

    protected <T> List<T> queryList(String sql, RowMapper<T> mapper, Object... params) {
        return withConnection(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindParams(ps, params);
                try (ResultSet rs = ps.executeQuery()) {
                    List<T> result = new ArrayList<>();
                    while (rs.next()) {
                        result.add(mapper.map(rs));
                    }
                    return result;
                }
            }
        });
    }

    protected int update(String sql, Object... params) {
        return withConnection(conn -> {
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                bindParams(ps, params);
                return ps.executeUpdate();
            }
        });
    }

    protected <T> T inTransaction(TransactionCallback<T> callback) {
        if (transactionConnection.get() != null) {
            try {
                return callback.execute(transactionConnection.get());
            } catch (SQLException e) {
                throw new DataAccessException("Lỗi transaction", e);
            }
        }
        try (Connection conn = dataSource.getConnection()) {
            boolean originalAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            transactionConnection.set(conn);
            try {
                T result = callback.execute(conn);
                conn.commit();
                return result;
            } catch (SQLException | RuntimeException | Error e) {
                try {
                    conn.rollback();
                } catch (SQLException rollbackError) {
                    e.addSuppressed(rollbackError);
                }
                if (e instanceof SQLException sqlError) {
                    throw new DataAccessException("Lỗi transaction", sqlError);
                }
                throw e;
            } finally {
                transactionConnection.remove();
                conn.setAutoCommit(originalAutoCommit);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi connection", e);
        }
    }

    private <T> T withConnection(TransactionCallback<T> callback) {
        Connection existing = transactionConnection.get();
        try {
            if (existing != null) {
                return callback.execute(existing);
            }
            try (Connection conn = dataSource.getConnection()) {
                return callback.execute(conn);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Lỗi truy cập dữ liệu", e);
        }
    }

    @FunctionalInterface
    public interface TransactionCallback<T> {
        T execute(Connection conn) throws SQLException;
    }

    private void bindParams(PreparedStatement ps, Object... params) throws SQLException {
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
    }
}
