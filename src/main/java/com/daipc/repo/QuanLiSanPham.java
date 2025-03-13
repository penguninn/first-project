package com.daipc.repo;

import com.daipc.enumm.TrangThai;
import com.daipc.enumm.TrangThaiCRUD;
import com.daipc.model.SanPham;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import net.miginfocom.layout.AC;

public class QuanLiSanPham implements Repository<SanPham> {

    private JDBCHelper dbHelper;

    @Override
    public List<SanPham> select(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        List<SanPham> listSP = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listSP.add(
                        new SanPham(
                                rs.getInt("id"),
                                rs.getString("masp"),
                                rs.getString("tensp"),
                                rs.getString("mota"),
                                rs.getString("hienthi")
                        )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSP;
    }
    
    @Override
    public ArrayList<SanPham> getAll(){
        ArrayList<SanPham> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaSP, TenSP from SanPham";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                SanPham  sanPham = new SanPham(id, MaSp, TenSP, null, null);
                list.add(sanPham);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public TrangThaiCRUD update(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        try {
            if (dbHelper.executeUpdate(sqlQuery, params) > 0) {
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

    public boolean checkExits(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    
}
