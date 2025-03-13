/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.enumm.TrangThai;
import com.daipc.model.MauSac;
import com.daipc.model.SanPham;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class MauSacRepo{
    private JDBCHelper dbHelper;
    
    
    public List<MauSac> select(String sqlQuery, Object... params) {
        dbHelper =  new JDBCHelper();
        List<MauSac> listMS = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while(rs.next()){
                listMS.add(
                    new MauSac(
                            rs.getInt("id"),
                            rs.getString("maMauSac"),
                            rs.getString("tenMauSac")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMS;
    }

    public int addDoDay(MauSac mauSac) {
        String sql = "INSERT INTO MauSac (MaMauSac, TenMauSac) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, mauSac.getMaMauSac(), mauSac.getTenMauSac());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(MauSac mauSac) {
        String sql = "UPDATE MauSac SET tenMauSac = ? WHERE maMauSac = ?";
        try {
            return dbHelper.executeUpdate(sql, mauSac.getTenMauSac(), mauSac.getMaMauSac());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    
    
    public ArrayList<MauSac> getAll(){
        ArrayList<MauSac> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaMauSac, TenMauSac from MauSac";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                MauSac  mauSac = new MauSac(id, MaSp, TenSP);
                list.add(mauSac);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public String generateMaMau() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaMauSac, 3, CHAR_LENGTH(MaMauSac) - 2) AS UNSIGNED))\n" +
                "FROM MauSac\n" +
                "WHERE MaMauSac LIKE 'MS%';\n";
        String maMauSac = ""; // Khởi tạo với chuỗi rỗng

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maMauSac = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Kiểm tra nếu maMauSac là chuỗi rỗng
        int nextId = 1;
        if (!maMauSac.isEmpty()) {
            nextId = Integer.parseInt(maMauSac) + 1;
        }

        return String.format("MS%03d", nextId);
    }
    
    public int deleteByMaMauSac(String maMauSac) {
        String sql = "DELETE FROM MauSac WHERE maMauSac = ?";
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(sql);
            ps.setString(1, maMauSac);
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
