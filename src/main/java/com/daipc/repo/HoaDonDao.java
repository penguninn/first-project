/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.HoaDonModel;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
    import java.sql.*;
import java.util.*;
import java.util.logging.*;
/**
 *
 * @author admin
 */
public class HoaDonDao {


private final Connection conn = DatabaseHelper.getConnection();

    public List<HoaDonModel> getListHD() {
    List<HoaDonModel> list = new ArrayList<>();
    String sql = """
        SELECT\s
            hd.ID AS id,\s
            hd.MaHD AS MaHD,
            kh.hoTen AS khachHangHoTen,\s
            kh.soDT AS khachHangSoDT,\s
            hd.tongGiaTriHoaDon AS tongGiaTri,\s
            hd.TrangThai AS TrangThai,
            hd.ngayTao AS ngayTao,\s
            nv.hoTen AS nhanVienHoTen
        FROM\s
            HoaDon hd
        INNER JOIN\s
            HoaDonCT hdct ON hdct.IDHoaDon = hd.ID
        RIGHT JOIN\s
            NhanVien nv ON nv.ID = hd.IDNhanVien
        LEFT JOIN\s
            KhachHang kh ON kh.ID = hd.IDKhachHang
        WHERE\s
            hd.TrangThai = 1
        GROUP BY\s
            hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;
    
    try (PreparedStatement stm = conn.prepareStatement(sql); 
         ResultSet rs = stm.executeQuery()) {
        while (rs.next()) {
            int id = rs.getInt("id");
            String maHD = rs.getString("MaHD");
            String tenKH = rs.getString("khachHangHoTen");
            String sdtKH = rs.getString("khachHangSoDT");
            BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
            String trangThai = rs.getString("TrangThai");
            Timestamp ngayTao = rs.getTimestamp("ngayTao");
            String tenNV = rs.getString("nhanVienHoTen");

            HoaDonModel hoaDonModel = new HoaDonModel();
            hoaDonModel.setId(id);
            hoaDonModel.setMaHD(maHD); // Assuming MaHD is an int field; adjust accordingly if it's a String
            hoaDonModel.setTenKH(tenKH);
            hoaDonModel.setSdt(sdtKH);
            hoaDonModel.setDonGia(tongGiaTri);
            hoaDonModel.setTrangThai(trangThai);
            hoaDonModel.setNgayTao(ngayTao);
            hoaDonModel.setTenNguoiTao(tenNV);

            list.add(hoaDonModel);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public List<HoaDonModel> selectBySQL(String sql, Object... args) {
    List<HoaDonModel> listHD = new ArrayList<>();
    ResultSet rs = null;
    try {
rs = DatabaseHelper.query(sql, args); // Lấy kết quả truy vấn
        ResultSetMetaData rsmd = rs.getMetaData(); // Lấy thông tin về các cột trong ResultSet
        int columnCount = rsmd.getColumnCount();
        Map<Integer, HoaDonModel> hdMap = new HashMap<>(); // Map để lưu trữ và loại bỏ trùng lặp

        while (rs.next()) {
            HoaDonModel hd = new HoaDonModel();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = rsmd.getColumnName(i);
                switch (columnName) { // Gán giá trị cho các thuộc tính của HoaDon dựa trên tên cột
                    case "MaHD" -> hd.setMaHD(rs.getString(i));
                    case "hoTen" -> hd.setTenKH(rs.getString(i));
                    case "soDT" -> hd.setSdt(rs.getString(i));
                    case "tongGiaTriHoaDon" -> hd.setDonGia(rs.getBigDecimal(i));
                    case "tenTTHD" -> hd.setTrangThai(rs.getString(i));
                    case "ngayTao" -> hd.setNgayTao(rs.getDate(i));
                    case "tenNV" -> hd.setTenNguoiTao(rs.getString(i));
                }
            }
            hdMap.put(hd.getId(), hd); // Lưu trữ vào map, tự động loại bỏ trùng lặp dựa trên maHD
        }

        listHD.addAll(hdMap.values()); // Thêm các giá trị không trùng lặp vào danh sách

    } catch (SQLException ex) {
        Logger.getLogger(HoaDonDao.class.getName()).log(Level.SEVERE, null, ex);
        System.out.println("Lỗi truy vấn danh sách hóa đơn");
    } finally {
        try {
            if (rs != null) {
                rs.close(); // Đóng ResultSet thủ công
            }
        } catch (SQLException ex) {
            Logger.getLogger(HoaDonDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    return listHD; // Trả về danh sách hóa đơn không trùng lặp
}
    
    public List<HoaDonModel> getSeachListHD(String HoaDon) {
    List<HoaDonModel> list = new ArrayList<>();
    String sql = """
                 
            SELECT\s
                hd.ID AS id,\s
                hd.MaHD AS MaHD,
                kh.hoTen AS khachHangHoTen,\s
                kh.soDT AS khachHangSoDT,\s
                hd.tongGiaTriHoaDon AS tongGiaTri,\s
                hd.TrangThai AS TrangThai,
                hd.ngayTao AS ngayTao,\s
                nv.hoTen AS nhanVienHoTen
            FROM\s
                HoaDon hd
            INNER JOIN\s
                HoaDonCT hdct ON hdct.IDHoaDon = hd.ID
            RIGHT JOIN\s
                NhanVien nv ON nv.ID = hd.IDNhanVien
            LEFT JOIN\s
                KhachHang kh ON kh.ID = hd.IDKhachHang
            WHERE\s
                hd.TrangThai = 1 AND hd.MaHD LIKE ?
            GROUP BY\s
                hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
        """;

        try (
                Connection conn = DatabaseHelper.getConnection();
                PreparedStatement stm = conn.prepareStatement(sql)) {

                stm.setString(1, "%" + HoaDon + "%"); // Correctly set the MaHD parameter

            try (ResultSet rs = stm.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String maHD = rs.getString("MaHD");
                    String tenKH = rs.getString("khachHangHoTen");
                    String sdtKH = rs.getString("khachHangSoDT");
                    BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                    String trangThai = rs.getString("TrangThai");
                    Timestamp ngayTao = rs.getTimestamp("ngayTao");
                    String tenNV = rs.getString("nhanVienHoTen");

                    HoaDonModel hoaDonModel = new HoaDonModel();
                    hoaDonModel.setId(id);
                    hoaDonModel.setMaHD(maHD); // Assuming MaHD is a String field
                    hoaDonModel.setTenKH(tenKH);
                    hoaDonModel.setSdt(sdtKH);
                    hoaDonModel.setDonGia(tongGiaTri);
                    hoaDonModel.setTrangThai(trangThai);
                    hoaDonModel.setNgayTao(ngayTao);
                    hoaDonModel.setTenNguoiTao(tenNV);

                    list.add(hoaDonModel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
}

    public List<HoaDonModel> getSeachList_SDT_HD(String sdt) {
    List<HoaDonModel> list = new ArrayList<>();
    String sql = """
        SELECT\s
            hd.ID AS id,\s
            hd.MaHD AS MaHD,
            kh.hoTen AS khachHangHoTen,\s
            kh.soDT AS khachHangSoDT,\s
            hd.tongGiaTriHoaDon AS tongGiaTri,\s
            hd.TrangThai AS TrangThai,
            hd.ngayTao AS ngayTao,\s
            nv.hoTen AS nhanVienHoTen
        FROM\s
            HoaDon hd
        INNER JOIN\s
            NhanVien nv ON nv.ID = hd.IDNhanVien
        LEFT JOIN\s
            KhachHang kh ON kh.ID = hd.IDKhachHang
        WHERE\s
            hd.TrangThai = 1 AND kh.SoDT LIKE ?
        GROUP BY\s
            hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;

    try (
        Connection conn = DatabaseHelper.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql)) {

        // Thiết lập tham số tìm kiếm số điện thoại
        stm.setString(1, "%" + sdt + "%");

        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String maHD = rs.getString("MaHD");
                String tenKH = rs.getString("khachHangHoTen");
                String sdtKH = rs.getString("khachHangSoDT");
                BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                String trangThai = rs.getString("TrangThai");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                String tenNV = rs.getString("nhanVienHoTen");

                HoaDonModel hoaDonModel = new HoaDonModel();
                hoaDonModel.setId(id);
                hoaDonModel.setMaHD(maHD);
                hoaDonModel.setTenKH(tenKH);
                hoaDonModel.setSdt(sdtKH);
                hoaDonModel.setDonGia(tongGiaTri);
                hoaDonModel.setTrangThai(trangThai);
                hoaDonModel.setNgayTao(ngayTao);
                hoaDonModel.setTenNguoiTao(tenNV);

                list.add(hoaDonModel);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}

    public List<HoaDonModel> getSeachList_TenKH_HD(String Ho_Ten) {
    List<HoaDonModel> list = new ArrayList<>();
    String sql = """
        SELECT\s
            hd.ID AS id,\s
            hd.MaHD AS MaHD,
            kh.hoTen AS khachHangHoTen,\s
            kh.soDT AS khachHangSoDT,\s
            hd.tongGiaTriHoaDon AS tongGiaTri,\s
            hd.TrangThai AS TrangThai,
            hd.ngayTao AS ngayTao,\s
            nv.hoTen AS nhanVienHoTen
        FROM\s
            HoaDon hd
        INNER JOIN\s
            NhanVien nv ON nv.ID = hd.IDNhanVien
        LEFT JOIN\s
            KhachHang kh ON kh.ID = hd.IDKhachHang
        WHERE\s
            hd.TrangThai = 1 AND kh.HoTen LIKE ?
        GROUP BY\s
            hd.ID, hd.MaHD, kh.hoTen, kh.soDT, hd.tongGiaTriHoaDon, hd.TrangThai, hd.ngayTao, nv.hoTen;
    """;

    try (
        Connection conn = DatabaseHelper.getConnection();
        PreparedStatement stm = conn.prepareStatement(sql)) {

        // Thiết lập tham số tìm kiếm số điện thoại
        stm.setString(1, "%" + Ho_Ten + "%");

        try (ResultSet rs = stm.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String maHD = rs.getString("MaHD");
                String tenKH = rs.getString("khachHangHoTen");
                String sdtKH = rs.getString("khachHangSoDT");
                BigDecimal tongGiaTri = rs.getBigDecimal("tongGiaTri");
                String trangThai = rs.getString("TrangThai");
                Timestamp ngayTao = rs.getTimestamp("ngayTao");
                String tenNV = rs.getString("nhanVienHoTen");

                HoaDonModel hoaDonModel = new HoaDonModel();
                hoaDonModel.setId(id);
                hoaDonModel.setMaHD(maHD);
                hoaDonModel.setTenKH(tenKH);
                hoaDonModel.setSdt(sdtKH);
                hoaDonModel.setDonGia(tongGiaTri);
                hoaDonModel.setTrangThai(trangThai);
                hoaDonModel.setNgayTao(ngayTao);
                hoaDonModel.setTenNguoiTao(tenNV);

                list.add(hoaDonModel);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}
