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

/**
 *
 * @author admin
 */
public class KhachHangService {
    
    private final Connection conn = DatabaseHelper.getConnection();

    public ArrayList<KhachHang> getAll() {
        ArrayList<KhachHang> listkh = new ArrayList<>();
        // Đảm bảo SELECT đầy đủ các cột cần thiết, ví dụ thêm NguoiTao nếu cần
        String sql = "SELECT ID, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NguoiTao FROM KhachHang";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getInt("ID"),
                        rs.getString("MaKhachHang"),
                        rs.getString("HoTen"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("SoDT"),
                        rs.getString("DiaChi"),
                        null,
                        rs.getString("NguoiTao")
                );
                listkh.add(kh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listkh;
    }



    public KhachHang get_TTKH_In_HD(String maHD) {
        String sql = """
        SELECT kh.ID, 
               kh.MaKhachHang, 
               kh.HoTen, 
               kh.GioiTinh, 
               kh.SoDT, 
               kh.DiaChi, 
               hd.TongGiaTriHoaDon
        FROM KhachHang kh
        INNER JOIN HoaDon hd ON kh.ID = hd.IDKhachHang
        WHERE hd.MaHD = ?
    """;

        KhachHang kh = null;

        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement stm = conn.prepareStatement(sql)) {

            stm.setString(1, maHD);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    kh = new KhachHang();
                    kh.setId(rs.getInt("ID"));
                    kh.setMaKhachHang(rs.getString("MaKhachHang"));
                    kh.setHoTen(rs.getString("HoTen"));
                    kh.setGioiTinh(rs.getBoolean("GioiTinh"));
                    kh.setSoDT(rs.getString("SoDT"));
                    kh.setDiaChi(rs.getString("DiaChi"));
                    kh.setTongGTHD(rs.getInt("TongGiaTriHoaDon"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }


}