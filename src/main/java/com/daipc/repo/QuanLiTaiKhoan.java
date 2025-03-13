/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author DaiPc
 */
public class QuanLiTaiKhoan {

    JDBCHelper dbHelper;
    private String sql = null;

    public ArrayList<NhanVien> getAccount() {
        dbHelper = new JDBCHelper();
        Connection con = dbHelper.getConnection();
        ArrayList<NhanVien> listNV = new ArrayList<>();

        sql =   "SELECT \n"
                + "id, \n"
                + "MaNhanVien,\n"
                + "HoTen,\n"
                + "SoDT,\n"
                + "CCCD,\n"
                + "NgaySinh,\n"
                + "ChucVu,\n"
                + "GioiTinh,\n"
                + "DiaChi,\n"
                + "TaiKhoan,\n"
                + "MatKhau,\n"
                + "NgayTao,\n"
                + "TrangThai\n"
                + "FROM \n"
                + "NhanVien;";

        try {
            ResultSet rs = dbHelper.executeQuery(sql);

            while (rs.next()) {
                listNV.add(new NhanVien(
                        rs.getInt("id"),
                        rs.getString("MaNhanVien"),
                        rs.getString("HoTen"),
                        rs.getString("SoDT"),
                        rs.getString("CCCD"),
                        rs.getDate("NgaySinh"),
                        rs.getString("ChucVu"),
                        rs.getBoolean("GioiTinh"),
                        rs.getString("DiaChi"),
                        rs.getString("TaiKhoan"),
                        rs.getString("MatKhau"),
                        rs.getDate("NgayTao"),
                        rs.getBoolean("TrangThai")
                ));
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNV;
    }

    public ArrayList<NhanVien> getDataNV(String MaNV) {
        dbHelper = new JDBCHelper();
        ArrayList<NhanVien> DataNV = new ArrayList<>();

        sql = "SELECT MaNhanVien, HoTen, SoDT, CCCD, NgaySinh, ChucVu, GioiTinh, DiaChi, TaiKhoan, MatKhau, NgayTao, TrangThai FROM NhanVien WHERE MaNhanVien = ?;";

        try {
            ResultSet rs = dbHelper.executeQuery(sql, MaNV);
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setCccd(rs.getString("CCCD"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setNgayTao(rs.getDate("NgayTao"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                DataNV.add(nv);
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DataNV;
    }

    public int doiMK(String MaNV, String mkMoi) {
        dbHelper = new JDBCHelper();
        sql = "UPDATE NhanVien SET MatKhau = ? WHERE MaNhanVien = ?";
        try {
            return dbHelper.executeUpdate(sql, mkMoi, MaNV);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    public int updateTrangThai(String MaNV, boolean trangthai) {
        dbHelper = new JDBCHelper();
        sql = "UPDATE NhanVien SET TrangThai = ? WHERE MaNhanVien = ?";
        try {
            return dbHelper.executeUpdate(sql, trangthai, MaNV);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public NhanVien checkTrung(String column, String value) {
        dbHelper = new JDBCHelper();
        sql = "SELECT TaiKhoan, CCCD FROM NhanVien WHERE " + column + " = ?";
        NhanVien nv = null;
        
        try {
            ResultSet rs = dbHelper.executeQuery(sql, value);
            
            while(rs.next()) {
                nv = new NhanVien(rs.getString("TaiKhoan"), 
                        rs.getString("CCCD")
                );
            }
            
            return nv;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public int add(NhanVien nv, String MaNVMoi) {
        dbHelper = new JDBCHelper();
        sql = "INSERT INTO NhanVien (MaNhanVien ,HoTen, SoDT, CCCD, NgaySinh, ChucVu, GioiTinh, DiaChi, TaiKhoan, MatKhau, NgayTao, TrangThai)\n"
                + "VALUES \n"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, GETDATE(), ?)";
        try {
            return dbHelper.executeUpdate(sql, MaNVMoi,
                    nv.getHoTen(),
                    nv.getSoDT(),
                    nv.getCccd(),
                    nv.getNgaySinh(),
                    nv.getChucVu(),
                    nv.isGioiTinh(),
                    nv.getDiaChi(),
                    nv.getTaiKhoan(),
                    nv.getMatKhau(),
                    nv.isTrangThai()
            );
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public int update(NhanVien nv, String maNV) {
        dbHelper = new JDBCHelper();
        sql = "UPDATE NhanVien SET "
                   + "HoTen = ?, "
                   + "SoDT = ?, "
                   + "CCCD = ?, "
                   + "NgaySinh = ?, "
                   + "ChucVu = ?, "
                   + "GioiTinh = ?, "
                   + "DiaChi = ?, "
                   + "TaiKhoan = ?, "
                   + "MatKhau = ?, "
                   + "TrangThai = ? "
                   + "WHERE MaNhanVien = ?";
        try {
            return dbHelper.executeUpdate(sql, 
                    nv.getHoTen(), 
                    nv.getSoDT(), 
                    nv.getCccd(),
                    nv.getNgaySinh(),
                    nv.getChucVu(),
                    nv.isGioiTinh(),
                    nv.getDiaChi(),
                    nv.getTaiKhoan(),
                    nv.getMatKhau(),
                    nv.isTrangThai(),
                    maNV);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<NhanVien> searchNhanVien(String keyword) {
        ArrayList<NhanVien> listNV = new ArrayList<>();
        String sql = "SELECT MaNhanVien, HoTen, SoDT, CCCD, NgaySinh, ChucVu, GioiTinh, DiaChi, TaiKhoan, MatKhau, NgayTao, TrangThai "
                   + "FROM NhanVien "
                   + "WHERE MaNhanVien LIKE ? OR HoTen LIKE ? OR CCCD LIKE ? OR TaiKhoan LIKE ?";

        try {
            PreparedStatement stmt = dbHelper.getConnection().prepareStatement(sql);
            stmt.setString(1, "%" + keyword + "%");
            stmt.setString(2, "%" + keyword + "%");
            stmt.setString(3, "%" + keyword + "%");
            stmt.setString(4, "%" + keyword + "%");

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                NhanVien nv = new NhanVien();
                nv.setMaNhanVien(rs.getString("MaNhanVien"));
                nv.setHoTen(rs.getString("HoTen"));
                nv.setSoDT(rs.getString("SoDT"));
                nv.setCccd(rs.getString("CCCD"));
                nv.setNgaySinh(rs.getDate("NgaySinh"));
                nv.setChucVu(rs.getString("ChucVu"));
                nv.setGioiTinh(rs.getBoolean("GioiTinh"));
                nv.setDiaChi(rs.getString("DiaChi"));
                nv.setTaiKhoan(rs.getString("TaiKhoan"));
                nv.setMatKhau(rs.getString("MatKhau"));
                nv.setNgayTao(rs.getDate("NgayTao"));
                nv.setTrangThai(rs.getBoolean("TrangThai"));
                
                listNV.add(nv);
            }
            dbHelper.closeResultSet(rs);
            dbHelper.closeStatement(stmt);
            dbHelper.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNV;
    }
}
