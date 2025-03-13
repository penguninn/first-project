package com.daipc.repo;

import com.daipc.model.DoDay;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DoDayRepo {
    private JDBCHelper dbHelper;

    public List<DoDay> select(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        List<DoDay> listDD = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listDD.add(
                        new DoDay(
                                rs.getInt("id"),
                                rs.getString("maDoDay"),
                                rs.getString("tenDoDay")
                        )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDD;
    }

    public int add(DoDay doDay) {
        String sql = "INSERT INTO DoDay (MaDoDay, TenDoDay) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, doDay.getMaDoDay(), doDay.getTenDoDay());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(DoDay doDay) {
        String sql = "UPDATE DoDay SET tenDoDay = ? WHERE maDoDay = ?";
        try {
            return dbHelper.executeUpdate(sql, doDay.getTenDoDay(), doDay.getMaDoDay());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<DoDay> getAll() {
        ArrayList<DoDay> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "SELECT ID, MaDoDay, TenDoDay FROM DoDay";
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                DoDay doDay = new DoDay(id, MaSp, TenSP);
                list.add(doDay);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateMaDoDay() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaDoDay, 3, LENGTH(MaDoDay) - 2) AS UNSIGNED)) FROM DoDay WHERE MaDoDay LIKE 'DD%'";
        String ma = ""; // Khởi tạo với chuỗi rỗng

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                ma = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Kiểm tra nếu ma là chuỗi rỗng hoặc null
        int nextId = 1;
        if (ma != null && !ma.isEmpty()) {
            nextId = Integer.parseInt(ma) + 1;
        }

        return String.format("DD%03d", nextId);
    }

    public int deleteByMa(String maDoDay) {
        String sql = "DELETE FROM DoDay WHERE maDoDay = ?";
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(sql);
            ps.setString(1, maDoDay);
            int row = ps.executeUpdate();
            if (row > 0) {
                return row;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}