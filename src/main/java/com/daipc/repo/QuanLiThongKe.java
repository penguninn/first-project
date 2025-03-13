/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import com.daipc.model.NhanVien;
import com.daipc.model.ThongKe;
import com.daipc.model.ThongKeDT;
import com.daipc.model.ThongKeKH;
import com.daipc.model.ThongKeNV;
import com.daipc.model.ThongKeSP;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 *
 * @author DaiPc
 */
public class QuanLiThongKe {

    JDBCHelper dBHelper;

    public ThongKe getCardThongKe() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """ 
                                SELECT 
                                        (SELECT COUNT(*) FROM HoaDon where trangthai = 1 or trangthai = 2) AS TotalOrders,
                                        (SELECT COUNT(*) FROM HoaDon where trangthai = 1) AS TotalOrders,
                                        (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon where trangthai = 1) AS TotalRevenue,
                                        (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalCost,
                                        (SELECT SUM(TongGiaTriHoaDon) FROM HoaDon) - (SELECT SUM(SoLuong * GiaNhap) FROM SanPhamChiTiet) AS TotalProfit
                            """;
        ThongKe thongKe = new ThongKe();

        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                thongKe.setDonHang(rs.getInt(1));
                thongKe.setDonThanhCong(rs.getInt(2));
                thongKe.setDonHuy(rs.getInt(1) - rs.getInt(2));
                thongKe.setDoanhThu(rs.getBigDecimal(3));
                thongKe.setChiPhi(rs.getBigDecimal(4));
                thongKe.setLoiNhuan(rs.getBigDecimal(5));
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return thongKe;
    }

    public List<ThongKeNV> getThongKeNV() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
                            SELECT
                                nv.HoTen AS TenNhanVien,
                                COUNT(hd.ID) AS SoLuongDonThanhCong,
                                SUM(hd.TongGiaTriHoaDon) AS TongGiaTriDonThanhCong,
                                COUNT(DISTINCT kh.ID) AS SoKhachHang
                            FROM
                                NhanVien nv
                            LEFT JOIN
                                HoaDon hd ON nv.ID = hd.IDNhanVien
                            LEFT JOIN
                                KhachHang kh ON hd.IDKhachHang = kh.ID
                            WHERE
                                hd.TrangThai = 1 
                            GROUP BY
                                nv.HoTen
                          """;
        List<ThongKeNV> listNV = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listNV.add(
                        new ThongKeNV(
                                rs.getString(1),
                                rs.getInt(2),
                                rs.getBigDecimal(3),
                                rs.getInt(4)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listNV;
    }

    public List<Integer> getYears() {
        dBHelper = new JDBCHelper();
        String sqlThang = """
                            SELECT DISTINCT
                                        YEAR(NgayTao) AS Nam
                                    FROM
                                        HoaDon
                                    WHERE
                                        TrangThai = 1
                                    ORDER BY
                                        Nam;
                          """;
        List<Integer> listYears = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlThang);
            while (rs.next()) {
                listYears.add(rs.getInt(1));
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listYears;
    }

    public List<ThongKeDT> getThongKeDTMonth(int year) {
        dBHelper = new JDBCHelper();
        String sqlThang = """
                            SELECT\s
                              CONCAT(YEAR(hd.NgayTao), '-', LPAD(MONTH(hd.NgayTao), 2, '0')) AS NamThang,
                              SUM(hd.TongGiaTriHoaDon) AS DoanhThu,
                              SUM(hd.TongGiaTriHoaDon) - SUM(IFNULL(costs.TotalCost, 0)) AS LoiNhuan
                          FROM HoaDon hd
                          LEFT JOIN (
                              SELECT\s
                                  hdct.IDHoaDon,
                                  SUM(spct.GiaNhap * hdct.SoLuong) AS TotalCost
                              FROM HoaDonCT hdct
                              JOIN SanPhamChiTiet spct ON spct.ID = hdct.IDCTSP
                              GROUP BY hdct.IDHoaDon
                          ) costs ON hd.ID = costs.IDHoaDon
                          WHERE hd.TrangThai = 1
                            AND YEAR(hd.NgayTao) = ?
                          GROUP BY CONCAT(YEAR(hd.NgayTao), '-', LPAD(MONTH(hd.NgayTao), 2, '0'))
                          ORDER BY NamThang;
                
                
                          """;
        List<ThongKeDT> listDT = new ArrayList<>();
        try {

            ResultSet rs = dBHelper.executeQuery(sqlThang, year);
            while (rs.next()) {
                listDT.add(
                        new ThongKeDT(
                                rs.getString(1),
                                rs.getBigDecimal(2),
                                rs.getBigDecimal(3)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDT;
    }

    public List<ThongKeDT> getThongKeDTYear() {
        dBHelper = new JDBCHelper();

        String sqlNam = """
    SELECT
        CAST(YEAR(hd.NgayTao) AS CHAR(4)) AS Nam,
        SUM(hd.TongGiaTriHoaDon) AS DoanhThu,
        SUM(hd.TongGiaTriHoaDon) - SUM(IFNULL(costs.TotalCost, 0)) AS LoiNhuan
    FROM HoaDon hd
    LEFT JOIN (
        SELECT 
            hdct.IDHoaDon,
            SUM(spct.GiaNhap * hdct.SoLuong) AS TotalCost
        FROM HoaDonCT hdct
        JOIN SanPhamChiTiet spct ON spct.ID = hdct.IDCTSP
        GROUP BY hdct.IDHoaDon
    ) costs ON hd.ID = costs.IDHoaDon
    WHERE hd.TrangThai = 1 
    GROUP BY CAST(YEAR(hd.NgayTao) AS CHAR(4))
    ORDER BY Nam;
""";

        List<ThongKeDT> listDT = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlNam);
            while (rs.next()) {
                listDT.add(
                        new ThongKeDT(
                                rs.getString(1),
                                rs.getBigDecimal(2),
                                rs.getBigDecimal(3)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listDT;
    }

    public List<ThongKeKH> getThongKeKH() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
        SELECT
            kh.MaKhachHang,
            kh.HoTen,
            COUNT(hd.ID) AS SoDonHang,
            SUM(hd.TongGiaTriHoaDon) AS TongGiaTriDonHang
        FROM KhachHang kh
        LEFT JOIN HoaDon hd ON kh.ID = hd.IDKhachHang
        WHERE hd.TrangThai = 1 -- Giả sử 1 là trạng thái "thành công"
        GROUP BY kh.MaKhachHang, kh.HoTen
        ORDER BY TongGiaTriDonHang DESC
        LIMIT 10;
        """;
        List<ThongKeKH> listKH = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listKH.add(
                        new ThongKeKH(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getBigDecimal(4)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listKH;
    }


    public int getSoLuongKH() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
                            SELECT COUNT(*) AS TongSoKhachHang
                                        FROM KhachHang;
                          """;
        int result = 0;
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                result = rs.getInt(1);
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<ThongKeSP> getThongKeSP() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
        WITH SanPhamBanChay AS (
            SELECT
                sp.ID AS SanPhamID,
                sp.MaSP,
                sp.TenSP,
                SUM(hdct.SoLuong) AS SoLuongBan
            FROM 
                SanPham sp
            JOIN 
                SanPhamChiTiet spct ON sp.ID = spct.IdSanPham
            JOIN 
                HoaDonCT hdct ON spct.ID = hdct.IDCTSP
            JOIN 
                HoaDon hd ON hdct.IDHoaDon = hd.ID
            WHERE 
                hd.TrangThai = 1
            GROUP BY 
                sp.ID, sp.MaSP, sp.TenSP
            ORDER BY 
                SoLuongBan DESC
            LIMIT 10
        )
        SELECT 
            spbc.MaSP,
            spbc.TenSP,
            spbc.SoLuongBan,
            IFNULL(SUM(spct.SoLuong), 0) AS SoLuongTonKho
        FROM 
            SanPhamBanChay spbc
        LEFT JOIN 
            SanPhamChiTiet spct ON spbc.SanPhamID = spct.IdSanPham
        GROUP BY 
            spbc.SanPhamID, spbc.MaSP, spbc.TenSP, spbc.SoLuongBan
        ORDER BY 
            spbc.SoLuongBan DESC
        """;
        List<ThongKeSP> listSP = new ArrayList<>();
        try {
            ResultSet rs = dBHelper.executeQuery(sqlQuery);
            while (rs.next()) {
                listSP.add(
                        new ThongKeSP(
                                rs.getString(1),
                                rs.getString(2),
                                rs.getInt(3),
                                rs.getInt(4)
                        )
                );
            }
            dBHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }


    ////////////////////////////////////////////////////////////////////////////
    public List<ChiTietSP> getThongKeSP_TBL() {
        dBHelper = new JDBCHelper();
        String sqlQuery = """
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
}
