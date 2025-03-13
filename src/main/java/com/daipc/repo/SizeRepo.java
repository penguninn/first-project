/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.repo;

import com.daipc.model.Size;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author acer
 */
public class SizeRepo {
    private JDBCHelper dbHelper;
    public List<Size> selectAllSize(String sql, Object... params) {
        dbHelper =  new JDBCHelper();
        List<Size> listSize = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sql, params);
            while(rs.next()){
                listSize.add(
                    new Size(
                            rs.getInt("id"),
                            rs.getString("maSize"),
                            rs.getString("tenSize")
                    )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listSize;
    }
    public int add(Size size) {
        String sql = "INSERT INTO Size (MaSize, TenSize) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, size.getMaSize(),size.getTenSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public int update(Size size) {
        String sql = "UPDATE Size SET TenSize = ? WHERE MaSize = ?";
        try {
            return dbHelper.executeUpdate(sql, size.getTenSize(), size.getMaSize());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<Size> getAll(){
        ArrayList<Size> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "select ID, MaSize, TenSize from Size";
        try {
            Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                Size  size = new Size(id, MaSp, TenSP);
                list.add(size);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public String generateMaSize() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaSize, 3, CHAR_LENGTH(MaSize) - 2) AS UNSIGNED))\n" +
                "FROM Size\n" +
                "WHERE MaSize LIKE 'SZ%';\n";
        String ma = ""; // Khởi tạo với chuỗi rỗng

        try (Connection conn = dbHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                ma = rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Kiểm tra nếu maMauSac là chuỗi rỗng
        int nextId = 1;
        if (!ma.isEmpty()) {
            nextId = Integer.parseInt(ma) + 1;
        }

        return String.format("SZ%03d", nextId);
    }
    
    public int deleteByMa(String maSize) {
        String sql = "DELETE FROM Size WHERE maSize = ?";
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(sql);
            ps.setString(1, maSize);
            int row = ps.executeUpdate();
            if (row > 0) {
                return row;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
