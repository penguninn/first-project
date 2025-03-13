/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.KhachHang;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class KhachHangRepo {
    JDBCHelper dBHelper;
    public KhachHangRepo() {
        dBHelper = new JDBCHelper();
    }
    public List<KhachHang> getAllKhachHang(){
        String sql = "select Id, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NgayTao, TrangThai from KhachHang";
        List<KhachHang> listKH = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sql);
            while (rs.next()) {
                listKH.add(
                    new KhachHang(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getBoolean(4),
                            rs.getString(5),
                            rs.getString(6),
                            rs.getString(7),
                            rs.getBoolean(8)
                    ));
            }   
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }
    
    public void addKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NgayTao, TrangThai) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = dBHelper.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, kh.getMaKhachHang());
            pstmt.setString(2, kh.getHoTen());
            pstmt.setBoolean(3, kh.isGioiTinh());
            pstmt.setString(4, kh.getSoDT());
            pstmt.setString(5, kh.getDiaChi());
            pstmt.setString(6, kh.getNgayTao());
            pstmt.setBoolean(7, kh.getTrangThai());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String generateMaKhachHang() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaKhachHang, 3, LENGTH(MaKhachHang) - 2) AS UNSIGNED)) FROM KhachHang WHERE MaKhachHang LIKE 'KH%'";
        String maKhachHangMax = ""; // Khởi tạo với chuỗi rỗng

        try (Connection conn = dBHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                maKhachHangMax = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Kiểm tra nếu maKhachHangMax là chuỗi rỗng
        int nextId = 1;
        if (!maKhachHangMax.isEmpty()) {
            nextId = Integer.parseInt(maKhachHangMax) + 1;
        }

        return String.format("KH%03d", nextId);
    }
    public boolean updateKhachHang(KhachHang kh) {
        try (Connection conn = dBHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                "UPDATE khachhang SET "
                + "HoTen = ?, "
                + "GioiTinh = ?, "
                + "SoDT = ?, "
                + "DiaChi = ?, "
                + "TrangThai = ? "
                + "WHERE MaKhachHang = ?")) {
            

            ps.setString(1, kh.getHoTen());
            ps.setBoolean(2, kh.isGioiTinh());
            ps.setString(3, kh.getSoDT());
            ps.setString(4, kh.getDiaChi());
            ps.setBoolean(5, kh.getTrangThai());
            ps.setString(6, kh.getMaKhachHang());
            

            int row = ps.executeUpdate();
            return row > 0; 
            
        } catch (SQLException e) {
            e.printStackTrace(); 
            return false; 
        }
    }
    public void updateTrangThai(String maKhachHang, String trangThai) {
        String sql = "UPDATE KhachHang SET TrangThai = ? WHERE MaKhachHang = ?";
        
        try (Connection conn = dBHelper.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             
            stmt.setString(1, trangThai);
            stmt.setString(2, maKhachHang);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<KhachHang> findKhachHangBySoDT(String soDT) {
    String sql = "SELECT Id, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NgayTao, TrangThai FROM KhachHang WHERE SoDT LIKE ?";
    List<KhachHang> listKH = new ArrayList<>();
    try (Connection conn = dBHelper.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, "%" + soDT + "%");
        ResultSet rs = pstmt.executeQuery();
        
        while (rs.next()) {
            listKH.add(
                new KhachHang(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getBoolean(4),
                    rs.getString(5),
                    rs.getString(6),
                    rs.getString(7),
                    rs.getBoolean(8)
                ));
        }
        dBHelper.closeResultSet(rs);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return listKH;
}

}
