/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.SPCT;
import com.daipc.model.Voucher;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author admin
 */
public class VoucherRepo {

    private final Connection conn = DatabaseHelper.getConnection();

    public List<Voucher> getAll() {
        ArrayList<Voucher> list = new ArrayList<>();
        String sql = "SELECT ID, MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa, TrangThai FROM Voucher;";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaVC = rs.getString(2);
                BigDecimal GiaTriVC = rs.getBigDecimal(3);
                Date NgayBatDau = rs.getDate(4);
                Date NgayKetThuc = rs.getDate(5);
                int Soluong = rs.getInt(6);
                String MoTa = rs.getString(7);
                int trangThai = rs.getInt(8);

                Voucher voucher = new Voucher(id, MaVC, GiaTriVC, NgayBatDau, NgayKetThuc, Soluong, MoTa, trangThai);
                list.add(voucher);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addVoucher(Voucher v) {
        String sql = "INSERT INTO Voucher (MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa, TrangThai)\n"
                + "VALUES \n"
                + "(?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, v.getMaVoucher());
            stm.setObject(2, v.getGiaTriVoucher());
            stm.setObject(3, v.getNgayBatDau());
            stm.setObject(4, v.getNgayKetThuc());
            stm.setObject(5, v.getSoLuong());
            stm.setObject(6, v.getMoTa());
            stm.setObject(7, v.getTrangThai());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateVoucher(Voucher v) {
        String sql = """
                     UPDATE Voucher 
                     SET 
                         GiaTriVoucher = ?,
                         NgayBatDau = ?,
                         NgayKetThuc = ?,
                         SoLuong = ?,
                         MoTa = ?,
                         TrangThai = ?
                     WHERE 
                         MaVoucher = ?;
                     """;

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            
            stm.setObject(1, v.getGiaTriVoucher());
            stm.setObject(2, v.getNgayBatDau());
            stm.setObject(3, v.getNgayKetThuc());
            stm.setObject(4, v.getSoLuong());
            stm.setObject(5, v.getMoTa());
            stm.setObject(6, v.getTrangThai());
            stm.setObject(7, v.getMaVoucher());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    public void delete(String ma) {
        String sql = """
                     DELETE Voucher 
                     
                     WHERE 
                         MaVoucher = ?;
                     """;

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            
            stm.setObject(1, ma);
            
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    
    
    public List<Voucher> seach(int TT) {
    ArrayList<Voucher> list = new ArrayList<>();
    String sql = """
                 SELECT ID, MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa, TrangThai 
                 FROM Voucher 
                 WHERE TrangThai = ?;
                 """;
    try {
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setObject(1, TT); // Thiết lập giá trị của tham số truy vấn
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt(1);
            String MaVC = rs.getString(2);
            BigDecimal GiaTriVC = rs.getBigDecimal(3);
            Date NgayBatDau = rs.getDate(4);
            Date NgayKetThuc = rs.getDate(5);
            int Soluong = rs.getInt(6);
            String MoTa = rs.getString(7);
            int trangThai = rs.getInt(8);

            Voucher voucher = new Voucher(id, MaVC, GiaTriVC, NgayBatDau, NgayKetThuc, Soluong, MoTa, trangThai);
            list.add(voucher); // Thêm voucher vào danh sách
        }
    } catch (Exception e) {
        e.printStackTrace(); // In ra lỗi nếu có
    }
    return list;
}

    
    public List<Voucher> searchGiaTriVC(BigDecimal minValue, BigDecimal maxValue) {
    ArrayList<Voucher> list = new ArrayList<>();
    String sql = """
                 SELECT ID, MaVoucher, GiaTriVoucher, NgayBatDau, NgayKetThuc, SoLuong, MoTa, TrangThai 
                 FROM Voucher 
                 WHERE GiaTriVoucher BETWEEN ? AND ?;
                 """;
    try {
        PreparedStatement stm = conn.prepareStatement(sql);
        stm.setBigDecimal(1, minValue);
        stm.setBigDecimal(2, maxValue);
        ResultSet rs = stm.executeQuery();
        
        while (rs.next()) {
            int id = rs.getInt(1);
            String MaVC = rs.getString(2);
            BigDecimal GiaTriVC = rs.getBigDecimal(3);
            Date NgayBatDau = rs.getDate(4);
            Date NgayKetThuc = rs.getDate(5);
            int Soluong = rs.getInt(6);
            String MoTa = rs.getString(7);
            int trangThai = rs.getInt(8);

            Voucher voucher = new Voucher(id, MaVC, GiaTriVC, NgayBatDau, NgayKetThuc, Soluong, MoTa, trangThai);
            list.add(voucher);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    
    
    
    
    public static void main(String[] args) {
        VoucherRepo repo = new VoucherRepo();
        Voucher voucher = (Voucher) repo.getAll();
        System.out.println(repo.getAll());
    }
}
