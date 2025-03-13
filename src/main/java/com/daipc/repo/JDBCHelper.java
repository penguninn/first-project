package com.daipc.repo;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHelper {

    private Connection connection = null;
    private final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private final String URL = "jdbc:mysql://localhost:3306/DuAn1_Final?useSSL=false&allowPublicKeyRetrieval=true";
    private final String USER = "root";
    private final String PASSWORD = "1234";

    public JDBCHelper() {
    }

    public Connection getConnection() {
        try {
            Class.forName(DRIVER);
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Connected to MySQL");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void closeStatement(PreparedStatement stmt) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResultSet(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public ResultSet executeQuery(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        return stmt.executeQuery();
    }

    public int executeUpdate(String query, Object... params) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt = conn.prepareStatement(query);

        for (int i = 0; i < params.length; i++) {
            stmt.setObject(i + 1, params[i]);
        }

        int rowsAffected = stmt.executeUpdate();
        closeStatement(stmt);
        closeConnection();

        return rowsAffected;
    }

    public static void main(String[] args) {
        try {
            DatabaseMetaData dbmt = new JDBCHelper().getConnection().getMetaData();
            System.out.println("Driver Name: " + dbmt.getDriverName());
            System.out.println("Database Product Name: " + dbmt.getDatabaseProductName());
            System.out.println("Database Product Version: " + dbmt.getDatabaseProductVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}