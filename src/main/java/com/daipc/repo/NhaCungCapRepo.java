/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.NhaCungCap;
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
public class NhaCungCapRepo {
    private JDBCHelper dbHelper;
    public List<NhaCungCap> selectAll(String sqlQuery, Object... params) {
        dbHelper =  new JDBCHelper();
        List<NhaCungCap> listNhaCC = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while(rs.next()){
                listNhaCC.add(
                    new NhaCungCap(
                            rs.getInt("id"),
                            rs.getString("maNhaCungCap"),
                            rs.getString("tenNhaCungCap"),
                            rs.getString("diaChi"),
                            rs.getString("lienHe")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNhaCC;
    }
    
    public int add(NhaCungCap NhaCc) {
        String sql = "INSERT INTO NhaCungCap (MaNhaCungCap, TenNhaCungCap, DiaChi, LienHe) VALUES (?, ?, ?, ?)";
        try {
            return dbHelper.executeUpdate(sql, NhaCc.getMaNhaCungCap(),NhaCc.getTenNhaCungCap(),NhaCc.getDiaChi(), NhaCc.getLienHe());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(NhaCungCap NhaCc) {
        String sql = "UPDATE NhaCungCap SET TenNhaCungCap = ?, DiaChi = ?, LienHe = ? WHERE MaNhaCungCap = ?";
        try {
            return dbHelper.executeUpdate(sql, NhaCc.getTenNhaCungCap(),NhaCc.getDiaChi(), NhaCc.getLienHe(), NhaCc.getMaNhaCungCap());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<NhaCungCap> getAll(){
        ArrayList<NhaCungCap> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaNhaCungCap, TenNhaCungCap from NhaCungCap";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                NhaCungCap  nhaCungCap = new NhaCungCap(id, MaSp, TenSP, MaSp, TenSP);
                list.add(nhaCungCap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
      return list;
    }
    public String generateMaNhaCungCap() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaNhaCungCap, 4, CHAR_LENGTH(MaNhaCungCap) - 3) AS UNSIGNED))\n" +
                "FROM NhaCungCap\n" +
                "WHERE MaNhaCungCap LIKE 'NCC%';\n";
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

        // Kiểm tra nếu maMauSac là chuỗi rỗng
        int nextId = 1;
        if (!ma.isEmpty()) {
            nextId = Integer.parseInt(ma) + 1;
        }

        return String.format("NCC%03d", nextId);
    }
    
    public int deleteByMa(String maNhaCungCap) {
        String sql = "DELETE FROM NhaCungCap WHERE maNhaCungCap = ?";
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(sql);
            ps.setString(1, maNhaCungCap);
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
