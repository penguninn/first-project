/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.enumm.TrangThaiCRUD;
import com.daipc.model.ChiTietSP;
import com.daipc.model.GioHang;
import com.daipc.model.HoaDon;
import com.daipc.model.HoaDonCho;
import com.daipc.model.KhachHang;
import com.daipc.model.PhuongThucTT;
import com.daipc.model.SanPham;
import com.daipc.model.Voucher;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DaiPc
 */
public class QuanLiBanHang {

    JDBCHelper dBHelper;

    public List<HoaDonCho> selectAllHDC() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
                            SELECT
                                hd.id,
                                hd.MaHD,
                                kh.HoTen AS TenKhachHang,
                                nv.HoTen AS TenNhanVien,
                                vc.MaVoucher,
                                GREATEST(
                                    COALESCE(SUM(CASE WHEN hdct.TrangThai = 1 THEN (hdct.SoLuong * hdct.DonGia) ELSE 0 END), 0) - COALESCE(vc.GiaTriVoucher, 0),
                                    0
                                ) AS ThanhToan,
                                pttt.TenPhuongThucTT,
                                hd.NgayTao,
                                hd.TrangThai,
                                COALESCE(SUM(CASE WHEN hdct.TrangThai = 1 THEN hdct.SoLuong * hdct.DonGia ELSE 0 END), 0) AS TongTien,
                                kh.sodt,
                                hd.ghiCHu,
                                vc.mota
                            FROM
                                hoadon hd
                                INNER JOIN KhachHang kh ON hd.IDKhachHang = kh.ID
                                INNER JOIN NhanVien nv ON hd.IDNhanVien = nv.ID
                                LEFT JOIN HoaDonCT hdct ON hd.ID = hdct.IDHoaDon
                                LEFT JOIN Voucher vc ON hd.IDVoucher = vc.ID
                                LEFT JOIN PhuongThucThanhToan pttt ON hd.IDPhuongThucTT = pttt.ID
                            where hd.trangthai = 0
                            GROUP BY 
                                hd.id, 
                                hd.MaHD, 
                                kh.HoTen, 
                                nv.HoTen, 
                                vc.MaVoucher, 
                                vc.GiaTriVoucher,
                                hd.NgayTao, 
                                hd.TrangThai,
                                kh.sodt,
                                pttt.TenPhuongThucTT,
                                hd.ghiCHu,
                                vc.mota
                        """;
        List<HoaDonCho> listHD = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listHD.add(
                        new HoaDonCho(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getString(4),
                                rs.getString(5),
                                rs.getBigDecimal(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getInt(9),
                                rs.getBigDecimal(10),
                                rs.getString(11),
                                rs.getString(12),
                                rs.getString(13)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<HoaDon> selectAllHD() {
        dBHelper = new JDBCHelper();
        List<HoaDon> listHD = new ArrayList<>();
        String sqlQuery = """
                            SELECT 
                                ID, 
                                MaHD, 
                                IDKhachHang, 
                                IDNhanVien, 
                                IDVoucher, 
                                TongGiaTriHoaDon, 
                                ThanhToan, 
                                NgayTao, 
                                TrangThai
                            FROM 
                                HoaDon;
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                int id = rs.getInt("ID");
                String maHD = rs.getString("MaHD");
                int idKhachHang = rs.getInt("IDKhachHang");
                int idNhanVien = rs.getInt("IDNhanVien");
                int idVoucher = rs.getInt("IDVoucher");
                double tongGiaTriHoaDon = rs.getDouble("TongGiaTriHoaDon");
                int thanhToan = rs.getInt("ThanhToan");
                String ngayTao = rs.getString("NgayTao");
                int trangThai = rs.getInt("TrangThai");

                listHD.add(new HoaDon(id, maHD, idKhachHang, idNhanVien, idVoucher,
                        tongGiaTriHoaDon, thanhToan, ngayTao, trangThai));
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listHD;
    }

    public List<GioHang> selectAllGH(String sqlQuery, Object... params) {
        dBHelper = new JDBCHelper();
        List<GioHang> listGH = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listGH.add(
                        new GioHang(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getBigDecimal(4),
                                rs.getInt(5),
                                rs.getBigDecimal(6),
                                rs.getBigDecimal(7)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listGH;
    }

    public List<ChiTietSP> selectAllSPCT(int column, int sort) {
        dBHelper = new JDBCHelper();

        // Xác định tên cột để sắp xếp dựa trên số cột (column) được truyền vào
        String[] columnNames = {
            "spct.MaSPCT",
            "spct.TenSPCT",
            "spct.GiaBan",
            "ms.TenMauSac",
            "s.TenSize",
            "cl.TenChatLieu",
            "dd.TenDoDay",
            "spct.SoLuong"
        };
        if (column < 0 || column >= columnNames.length) {
            throw new IllegalArgumentException("Invalid column index");
        }
        String sqlQuery;
        if (sort == 0) {
            sqlQuery = """
                SELECT 
                    spct.id,
                    spct.MaSPCT,
                    spct.TenSPCT,
                    spct.GiaBan,
                    ms.TenMauSac,
                    s.TenSize,
                    cl.TenChatLieu,
                    dd.TenDoDay,
                    ncc.TenNhaCungCap,
                    spct.SoLuong
                FROM 
                    SanPhamChiTiet spct
                LEFT JOIN 
                    SanPham sp ON spct.IdSanPham = sp.ID
                LEFT JOIN 
                    MauSac ms ON spct.IdMauSac = ms.ID
                LEFT JOIN 
                    Size s ON spct.IdSize = s.ID
                LEFT JOIN 
                    ChatLieu cl ON spct.IdChatLieu = cl.ID
                LEFT JOIN 
                    DoDay dd ON spct.IdDoDay = dd.ID
                LEFT JOIN 
                    NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
                WHERE spct.hienthi = 'hien'
                """;
        } else {
            // Xây dựng câu lệnh SQL với sắp xếp động
            String orderDirection = (sort == 1) ? "ASC" : "DESC";
            sqlQuery = String.format("""
                SELECT 
                    spct.id,
                    spct.MaSPCT,
                    spct.TenSPCT,
                    spct.GiaBan,
                    ms.TenMauSac,
                    s.TenSize,
                    cl.TenChatLieu,
                    dd.TenDoDay,
                    ncc.TenNhaCungCap,
                    spct.SoLuong
                FROM 
                    SanPhamChiTiet spct
                LEFT JOIN 
                    SanPham sp ON spct.IdSanPham = sp.ID
                LEFT JOIN 
                    MauSac ms ON spct.IdMauSac = ms.ID
                LEFT JOIN 
                    Size s ON spct.IdSize = s.ID
                LEFT JOIN 
                    ChatLieu cl ON spct.IdChatLieu = cl.ID
                LEFT JOIN 
                    DoDay dd ON spct.IdDoDay = dd.ID
                LEFT JOIN 
                    NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
                WHERE spct.hienthi = 'hien'
                ORDER BY %s %s
                """, columnNames[column], orderDirection);
        }

        List<ChiTietSP> listSPCT = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listSPCT.add(
                        new ChiTietSP(
                                rs.getInt(1),
                                rs.getString(2),
                                rs.getString(3),
                                rs.getBigDecimal(4),
                                rs.getString(5),
                                rs.getString(6),
                                rs.getString(7),
                                rs.getString(8),
                                rs.getString(9),
                                rs.getInt(10)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSPCT;
    }

    public List<KhachHang> getAllKH() {
        dBHelper = new JDBCHelper();
        List<KhachHang> listKH = new ArrayList<>();
        String sqlQuery = """
                            select id, MaKhachHang, HoTen, GioiTinh, SoDT, DiaChi, NguoiTao, NgayTao from KhachHang
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listKH.add(new KhachHang(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8)
                ));
            }
        } catch (Exception e) {
        }
        return listKH;
    }

    public Voucher getVoucher(String maVoucher) {
        dBHelper = new JDBCHelper();
        Voucher vc = new Voucher();
        String sqlQuery = """
                            select * from Voucher where GETDATE() <= NgayKetThuc and SoLuong > 0 and MaVoucher like ?
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery, maVoucher);
            while (rs.next()) {
                vc = (new Voucher(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getBigDecimal(3),
                        rs.getDate(4),
                        rs.getDate(5),
                        rs.getInt(6),
                        rs.getString(7)
                ));
            }
        } catch (Exception e) {
        }
        return vc;
    }

    public List<PhuongThucTT> getAllPhuongThucTT() {
        dBHelper = new JDBCHelper();
        List<PhuongThucTT> listPTTT = new ArrayList<>();
        String sqlQuery = """
                            select * from Phuongthucthanhtoan
                          """;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listPTTT.add(new PhuongThucTT(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3)
                ));
            }
        } catch (Exception e) {
        }
        return listPTTT;
    }

    public TrangThaiCRUD update(String sqlQuery, Object... params) {
        dBHelper = new JDBCHelper();
        try {
            if (dBHelper.executeUpdate(sqlQuery, params) > 0) {
                return TrangThaiCRUD.ThanhCong;
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception: " + e.getMessage());
            if ("23000".equals(e.getSQLState()) || e.getErrorCode() == 2627 || e.getErrorCode() == 2601 || "DaTonTai".equals(e.getMessage())) {
                return TrangThaiCRUD.DaTonTai;
            } else {
                e.printStackTrace();
            }
        }
        return TrangThaiCRUD.ThatBai;
    }
}
