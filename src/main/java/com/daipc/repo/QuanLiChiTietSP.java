/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

//import com.daipc.repo.DB_connect;
import com.daipc.repo.DatabaseHelper;
//import com.daipc.repo.JDBCHelper;
import java.sql.Connection;
import java.util.List;

/**
 *
 * @author admin
 */
import com.daipc.model.ChiTietSP;
import com.daipc.model.SPCT;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class QuanLiChiTietSP {

    private final Connection conn = DatabaseHelper.getConnection();

    public List<ChiTietSP> getAll() {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "     A.ID,\n"
                + "    A.MaSPCT,\n"
                + "    B.MaSP,\n"
                + "    B.TenSP,\n"
                + "    A.TenSPCT,\n"
                + "    A.GiaBan,\n"
                + "    A.SoLuong,\n"
                + "    G.TenDoDay,\n"
                + "    E.TenSize,\n"
                + "    F.TenChatLieu,\n"
                + "    D.TenMauSac,\n"
                + "    C.TenNhaCungCap,\n"
                + "    A.TrangThai,\n"
                + "    A.GiaNhap\n"
                + "FROM \n"
                + "    SanPhamChiTiet A \n"
                + "LEFT JOIN \n"
                + "    SanPham B ON A.IdSanPham = B.ID\n"
                + "LEFT JOIN \n"
                + "    NhaCungCap C ON A.IdNhaCungCap = C.ID \n"
                + "LEFT JOIN \n"
                + "    MauSac D ON A.IdMauSac = D.ID\n"
                + "LEFT JOIN \n"
                + "    Size E ON A.IdSize = E.ID\n"
                + "LEFT JOIN \n"
                + "    ChatLieu F ON A.IdChatLieu = F.ID\n"
                + "LEFT JOIN \n"
                + "    DoDay G ON A.IdDoDay = G.ID where A.hienthi like 'Hien'";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSPCT = rs.getString(2);
                String MaSP = rs.getString(3);
                String TenSP = rs.getString(4);
                String TenSPCT = rs.getString(5);
                BigDecimal GiaBan = rs.getBigDecimal(6);
                BigDecimal GiaNhap = rs.getBigDecimal(14);
                int Soluong = rs.getInt(7);
                String tenDoDay = rs.getString(8);
                String tenSize = rs.getString(9);
                String tenCL = rs.getString(10);
                String tenMS = rs.getString(12);
                String tenNCC = rs.getString(11);
                String trangThai = rs.getString(13);

                ChiTietSP chiTietSP = new ChiTietSP(id, MaSPCT, MaSP, TenSP, TenSPCT, GiaBan, GiaNhap, Soluong,
                        tenMS, tenNCC, tenSize, tenCL, tenDoDay, trangThai);
                list.add(chiTietSP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void Update(SPCT spct, String ma) {
        String sql = "UPDATE SanPhamChiTiet SET MaSPCT = ?,SoLuong  = ?, IdSanPham = ?, "
                + "IdMauSac = ?, IdSize = ?, IdChatLieu = ?,IdDoDay=?, IdNhaCungCap = ?, GiaBan = ?,"
                + " GiaNhap = ?, TrangThai = ?,TenSPCT=?  WHERE MaSPCT = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, spct.getMaSPCT());
            stm.setObject(2, spct.getSoLuong());
            stm.setObject(3, spct.getIdSanPham());
            stm.setObject(4, spct.getIdMauSac());
            stm.setObject(5, spct.getIdSize());
            stm.setObject(6, spct.getIdChatLieu());
            stm.setObject(7, spct.getIdDoDay());
            stm.setObject(8, spct.getIdNhaCungCap());
            stm.setObject(9, spct.getGiaBan());
            stm.setObject(10, spct.getGiaNhap());
            stm.setObject(11, spct.getTrangThai());
            stm.setObject(12, spct.getTenSPCT());
            stm.setObject(13, spct.getMaSPCT());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<ChiTietSP> timKiemSp(String sanPham) {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "	A.ID,\n"
                + "    A.MaSPCT,\n"
                + "    B.MaSP,\n"
                + "    B.TenSP,\n"
                + "    A.TenSPCT,\n"
                + "    A.GiaBan,\n"
                + "    A.SoLuong,\n"
                + "    G.TenDoDay,\n"
                + "    E.TenSize,\n"
                + "    F.TenChatLieu,\n"
                + "    D.TenMauSac,\n"
                + "    C.TenNhaCungCap,\n"
                + "    A.TrangThai,\n"
                + "    A.GiaNhap\n"
                + "FROM \n"
                + "    SanPhamChiTiet A \n"
                + "LEFT JOIN \n"
                + "    SanPham B ON A.IdSanPham = B.ID\n"
                + "LEFT JOIN \n"
                + "    NhaCungCap C ON A.IdNhaCungCap = C.ID \n"
                + "LEFT JOIN \n"
                + "    MauSac D ON A.IdMauSac = D.ID\n"
                + "LEFT JOIN \n"
                + "    Size E ON A.IdSize = E.ID\n"
                + "LEFT JOIN \n"
                + "    ChatLieu F ON A.IdChatLieu = F.ID\n"
                + "LEFT JOIN \n"
                + "    DoDay G ON A.IdDoDay = G.ID\n"
                + "	WHERE B.TenSP = ?";
        try {
            Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, sanPham);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietSP chiTietSP = new ChiTietSP();
                chiTietSP.setId(rs.getInt(1));
                chiTietSP.setMaCTSP(rs.getString(2));
                chiTietSP.setMaSP(rs.getString(3));
                chiTietSP.setTenSP(rs.getString(4));
                chiTietSP.setTenSPCT(rs.getString(5));
                chiTietSP.setGiaBan(rs.getBigDecimal(6));
                chiTietSP.setSoLuong(rs.getInt(7));
                chiTietSP.setTenDoDay(rs.getString(8));
                chiTietSP.setTenSize(rs.getString(9));
                chiTietSP.setTenChatLieu(rs.getString(10));
                chiTietSP.setTenMauSac(rs.getString(11));
                chiTietSP.setTenNhaCungCap(rs.getString(12));
                chiTietSP.setTrangThai(rs.getString(13));
                chiTietSP.setGiaNhap(rs.getBigDecimal(14));
                list.add(chiTietSP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public ArrayList<SPCT> getAllAnh() {
        ArrayList<SPCT> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, Anh from SanPhamChiTiet";
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);

                String TenSP = rs.getString(2);
                SPCT spct = new SPCT(id, TenSP, id, id, id, id, id, id, id, sql, null, null, BigDecimal.ONE, BigDecimal.TEN, sql, TenSP);
                list.add(spct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void addCTSP1(SPCT spct) {
        String sql = "INSERT INTO SanPhamChiTiet (MaSPCT, SoLuong, IdSanPham, IdMauSac, IdSize, IdChatLieu, IdDoDay, IdNhaCungCap, GiaNhap, GiaBan, TrangThai, TenSPCT) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, spct.getMaSPCT());
            stm.setObject(2, spct.getSoLuong());
            stm.setObject(3, spct.getIdSanPham());
            stm.setObject(4, spct.getIdMauSac());
            stm.setObject(5, spct.getIdSize());
            stm.setObject(6, spct.getIdChatLieu());
            stm.setObject(7, spct.getIdDoDay());
            stm.setObject(8, spct.getIdNhaCungCap());
            stm.setObject(9, spct.getGiaNhap());
            stm.setObject(10, spct.getGiaBan());
            stm.setObject(11, spct.getTrangThai());
            stm.setObject(12, spct.getTenSPCT());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<ChiTietSP> getSPCT(int idSP) {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = "SELECT \n"
                + "     A.ID,\n"
                + "    A.MaSPCT,\n"
                + "    B.MaSP,\n"
                + "    B.TenSP,\n"
                + "    A.TenSPCT,\n"
                + "    A.GiaBan,\n"
                + "    A.SoLuong,\n"
                + "    G.TenDoDay,\n"
                + "    E.TenSize,\n"
                + "    F.TenChatLieu,\n"
                + "    D.TenMauSac,\n"
                + "    C.TenNhaCungCap,\n"
                + "    A.TrangThai,\n"
                + "    A.GiaNhap\n"
                + "FROM \n"
                + "    SanPhamChiTiet A \n"
                + "LEFT JOIN \n"
                + "    SanPham B ON A.IdSanPham = B.ID\n"
                + "LEFT JOIN \n"
                + "    NhaCungCap C ON A.IdNhaCungCap = C.ID \n"
                + "LEFT JOIN \n"
                + "    MauSac D ON A.IdMauSac = D.ID\n"
                + "LEFT JOIN \n"
                + "    Size E ON A.IdSize = E.ID\n"
                + "LEFT JOIN \n"
                + "    ChatLieu F ON A.IdChatLieu = F.ID\n"
                + "LEFT JOIN \n"
                + "    DoDay G ON A.IdDoDay = G.ID where A.hienthi like 'Hien' and A.IdSanPham = ?";
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, idSP);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSPCT = rs.getString(2);
                String MaSP = rs.getString(3);
                String TenSP = rs.getString(4);
                String TenSPCT = rs.getString(5);
                BigDecimal GiaBan = rs.getBigDecimal(6);
                BigDecimal GiaNhap = rs.getBigDecimal(14);
                int Soluong = rs.getInt(7);
                String tenDoDay = rs.getString(8);
                String tenSize = rs.getString(9);
                String tenCL = rs.getString(10);
                String tenMS = rs.getString(12);
                String tenNCC = rs.getString(11);
                String trangThai = rs.getString(13);

                ChiTietSP chiTietSP = new ChiTietSP(id, MaSPCT, MaSP, TenSP, TenSPCT, GiaBan, GiaNhap, Soluong,
                        tenMS, tenNCC, tenSize, tenCL, tenDoDay, trangThai);
                list.add(chiTietSP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ChiTietSP> seachtTT(String TT) {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = """
                     SELECT 
                                                               A.ID,
                                                              A.MaSPCT,
                                                             B.MaSP,
                                                            B.TenSP,
                                                             A.TenSPCT,
                                                              A.GiaBan,
                                                              A.SoLuong,
                                                             G.TenDoDay,
                                                              E.TenSize,
                                                              F.TenChatLieu,
                                                              D.TenMauSac,
                                                              C.TenNhaCungCap,
                                                            A.TrangThai,
                                                              A.GiaNhap
                                                          FROM 
                                                             SanPhamChiTiet A 
                                                          LEFT JOIN 
                                                              SanPham B ON A.IdSanPham = B.ID
                                                          LEFT JOIN 
                                                              NhaCungCap C ON A.IdNhaCungCap = C.ID 
                                                          LEFT JOIN 
                                                              MauSac D ON A.IdMauSac = D.ID
                                                          LEFT JOIN 
                                                              Size E ON A.IdSize = E.ID
                                                          LEFT JOIN 
                                                            ChatLieu F ON A.IdChatLieu = F.ID
                                                          LEFT JOIN 
                                                             DoDay G ON A.IdDoDay = G.ID where A.hienthi like 'Hien'  AND A.TrangThai = ?
                     """;
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, TT);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSPCT = rs.getString(2);
                String MaSP = rs.getString(3);
                String TenSP = rs.getString(4);
                String TenSPCT = rs.getString(5);
                BigDecimal GiaBan = rs.getBigDecimal(6);
                BigDecimal GiaNhap = rs.getBigDecimal(14);
                int Soluong = rs.getInt(7);
                String tenDoDay = rs.getString(8);
                String tenSize = rs.getString(9);
                String tenCL = rs.getString(10);
                String tenMS = rs.getString(12);
                String tenNCC = rs.getString(11);
                String trangThai = rs.getString(13);

                ChiTietSP chiTietSP = new ChiTietSP(id, MaSPCT, MaSP, TenSP, TenSPCT, GiaBan, GiaNhap, Soluong,
                        tenMS, tenNCC, tenSize, tenCL, tenDoDay, trangThai);
                list.add(chiTietSP);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<ChiTietSP> timKiemGiaNhap(BigDecimal minValue, BigDecimal maxValue) {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = """
                     SELECT 
                                          A.ID,
                                         A.MaSPCT,
                                        B.MaSP,
                                       B.TenSP,
                                        A.TenSPCT,
                                         A.GiaBan,
                                         A.SoLuong,
                                        G.TenDoDay,
                                         E.TenSize,
                                         F.TenChatLieu,
                                         D.TenMauSac,
                                         C.TenNhaCungCap,
                                       A.TrangThai,
                                         A.GiaNhap
                                     FROM 
                                        SanPhamChiTiet A 
                                     LEFT JOIN 
                                         SanPham B ON A.IdSanPham = B.ID
                                     LEFT JOIN 
                                         NhaCungCap C ON A.IdNhaCungCap = C.ID 
                                     LEFT JOIN 
                                         MauSac D ON A.IdMauSac = D.ID
                                     LEFT JOIN 
                                         Size E ON A.IdSize = E.ID
                                     LEFT JOIN 
                                       ChatLieu F ON A.IdChatLieu = F.ID
                                     LEFT JOIN 
                                        DoDay G ON A.IdDoDay = G.ID where A.hienthi like 'Hien'  AND a.GiaNhap BETWEEN ? AND ?
                     """;
        try {
            Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, minValue);
            stm.setObject(2, maxValue);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietSP chiTietSP = new ChiTietSP();
                chiTietSP.setId(rs.getInt(1));
                chiTietSP.setMaCTSP(rs.getString(2));
                chiTietSP.setMaSP(rs.getString(3));
                chiTietSP.setTenSP(rs.getString(4));
                chiTietSP.setTenSPCT(rs.getString(5));
                chiTietSP.setGiaBan(rs.getBigDecimal(6));
                chiTietSP.setSoLuong(rs.getInt(7));
                chiTietSP.setTenDoDay(rs.getString(8));
                chiTietSP.setTenSize(rs.getString(9));
                chiTietSP.setTenChatLieu(rs.getString(10));
                chiTietSP.setTenMauSac(rs.getString(11));
                chiTietSP.setTenNhaCungCap(rs.getString(12));
                chiTietSP.setTrangThai(rs.getString(13));
                chiTietSP.setGiaNhap(rs.getBigDecimal(14));
                list.add(chiTietSP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }

    public List<ChiTietSP> timKiemGiaBan(BigDecimal minValue, BigDecimal maxValue) {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = """
                     SELECT 
                                          A.ID,
                                         A.MaSPCT,
                                        B.MaSP,
                                       B.TenSP,
                                        A.TenSPCT,
                                         A.GiaBan,
                                         A.SoLuong,
                                        G.TenDoDay,
                                         E.TenSize,
                                         F.TenChatLieu,
                                         D.TenMauSac,
                                         C.TenNhaCungCap,
                                       A.TrangThai,
                                         A.GiaNhap
                                     FROM 
                                        SanPhamChiTiet A 
                                     LEFT JOIN 
                                         SanPham B ON A.IdSanPham = B.ID
                                     LEFT JOIN 
                                         NhaCungCap C ON A.IdNhaCungCap = C.ID 
                                     LEFT JOIN 
                                         MauSac D ON A.IdMauSac = D.ID
                                     LEFT JOIN 
                                         Size E ON A.IdSize = E.ID
                                     LEFT JOIN 
                                       ChatLieu F ON A.IdChatLieu = F.ID
                                     LEFT JOIN 
                                        DoDay G ON A.IdDoDay = G.ID where A.hienthi like 'Hien'  AND A.GiaBan BETWEEN ? AND ?
                     """;
        try {
            Connection conn = DatabaseHelper.getConnection();
            PreparedStatement stm = conn.prepareStatement(sql);
            stm.setObject(1, minValue);
            stm.setObject(2, maxValue);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ChiTietSP chiTietSP = new ChiTietSP();
                chiTietSP.setId(rs.getInt(1));
                chiTietSP.setMaCTSP(rs.getString(2));
                chiTietSP.setMaSP(rs.getString(3));
                chiTietSP.setTenSP(rs.getString(4));
                chiTietSP.setTenSPCT(rs.getString(5));
                chiTietSP.setGiaBan(rs.getBigDecimal(6));
                chiTietSP.setSoLuong(rs.getInt(7));
                chiTietSP.setTenDoDay(rs.getString(8));
                chiTietSP.setTenSize(rs.getString(9));
                chiTietSP.setTenChatLieu(rs.getString(10));
                chiTietSP.setTenMauSac(rs.getString(11));
                chiTietSP.setTenNhaCungCap(rs.getString(12));
                chiTietSP.setTrangThai(rs.getString(13));
                chiTietSP.setGiaNhap(rs.getBigDecimal(14));
                list.add(chiTietSP);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
    
    public List<ChiTietSP> Get_GiaBan() {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = """
                     SELECT MAX(GiaBan) AS MaxGiaBan 
                                                                                                        
                                        FROM SanPhamChiTiet 
                     """;
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                // Retrieve the maximum value
                BigDecimal maxGiaBan = resultSet.getBigDecimal("MaxGiaBan");
                ChiTietSP chiTietSP = new ChiTietSP(0, sql, sql, maxGiaBan, sql, sql, sql, sql, sql, 0);
                list.add(chiTietSP);
                System.out.println("The maximum GiaBan value is: " + maxGiaBan);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ChiTietSP> Get_GiaNhap() {
        ArrayList<ChiTietSP> list = new ArrayList<>();
        String sql = """
                        SELECT MAX(GiaNhap) AS MaxGiaNhap 
                         FROM SanPhamChiTiet 
                     """;
        try {
            PreparedStatement stm = conn.prepareStatement(sql);
            ResultSet resultSet = stm.executeQuery();

            while (resultSet.next()) {
                // Retrieve the maximum value
                BigDecimal maxGiaNhap = resultSet.getBigDecimal("MaxGiaNhap");
                ChiTietSP chiTietSP = new ChiTietSP(0, sql, sql, maxGiaNhap, sql, sql, sql, sql, sql, 0);
                list.add(chiTietSP);
                System.out.println("The maximum GiaNhap value is: " + maxGiaNhap);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args) {
        QuanLiChiTietSP ctsps = new QuanLiChiTietSP();
        System.out.println(ctsps.getAll());
    }

}
