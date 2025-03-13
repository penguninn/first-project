package com.daipc.repo;

import com.daipc.model.ChiTietSP;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO class for product details
 */
public class ProductDetaisDAO {

    /**
     * Select all product details by invoice ID
     * 
     * @param maHD Invoice ID
     * @return List of ChiTietSP objects
     */
    public List<ChiTietSP> selectAll_By_ID_MaHD(String maHD) {
        String sql = """
            SELECT 
                spct.ID, 
                sp.TenSP AS tenSanPham, 
                ncc.TenNhaCungCap AS tenThuongHieu,
                sz.TenSize AS kichCo,
                ms.TenMauSac AS tenMauSac, 
                hdct.SoLuong AS soLuong,  
                hdct.DonGia AS giaBan,
                cl.TenChatLieu AS tenChatLieu, 
                dd.TenDoDay AS tenDoDay,  
                spct.TrangThai,
                spct.MaSPCT
            FROM 
                SanPhamChiTiet spct
            LEFT JOIN 
                SanPham sp ON spct.IdSanPham = sp.ID
            LEFT JOIN 
                NhaCungCap ncc ON spct.IdNhaCungCap = ncc.ID
            LEFT JOIN 
                ChatLieu cl ON spct.IdChatLieu = cl.ID
            LEFT JOIN 
                DoDay dd ON spct.IdDoDay = dd.ID
            LEFT JOIN 
                Size sz ON spct.IdSize = sz.ID
            LEFT JOIN 
                MauSac ms ON spct.IdMauSac = ms.ID
            LEFT JOIN 
                HoaDonCT hdct ON hdct.IDCTSP = spct.ID
            LEFT JOIN 
                HoaDon hd ON hd.ID = hdct.IDHoaDon
            WHERE 
                spct.HienThi = 'Hien' AND hd.MaHD = ?;
        """;
        return selectBySQL(sql, maHD);
    }
    
    
    

    /**
     * Execute a SQL query and return a list of ChiTietSP
     * 
     * @param sql SQL query string
     * @param args Parameters for the SQL query
     * @return List of ChiTietSP objects
     */
    public List<ChiTietSP> selectBySQL(String sql, Object... args) {
        List<ChiTietSP> listProdetails = new ArrayList<>();
        
        try (Connection conn = DatabaseHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            // Set parameters
            for (int i = 0; i < args.length; i++) {
                pstmt.setObject(i + 1, args[i]);
            }
            
            // Execute query
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietSP prodetails = new ChiTietSP();
                    prodetails.setId(rs.getInt("ID"));
                    prodetails.setTenChatLieu(rs.getString("tenChatLieu"));
                    prodetails.setTenDoDay(rs.getString("tenDoDay"));
                    prodetails.setTenSize(rs.getString("kichCo"));
                    prodetails.setTenMauSac(rs.getString("tenMauSac"));
                    prodetails.setTenSP(rs.getString("tenSanPham"));
                    prodetails.setTenNhaCungCap(rs.getString("tenThuongHieu"));
                    prodetails.setSoLuong(rs.getInt("soLuong"));
                    prodetails.setGiaBan(rs.getBigDecimal("giaBan"));
                    prodetails.setTrangThai(rs.getString("TrangThai"));
                    prodetails.setMaCTSP(rs.getString("MaSPCT"));
                    listProdetails.add(prodetails);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ProductDetaisDAO.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return listProdetails;
    }

    public static void main(String[] args) {
        ProductDetaisDAO detaisDAO = new ProductDetaisDAO();
        System.out.println(detaisDAO.selectAll_By_ID_MaHD("HD003"));
    }
}
