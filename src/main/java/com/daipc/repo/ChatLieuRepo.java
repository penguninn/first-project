package com.daipc.repo;

import com.daipc.model.ChatLieu;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChatLieuRepo {
    private JDBCHelper dbHelper;

    public List<ChatLieu> selectAll(String sqlQuery, Object... params) {
        dbHelper = new JDBCHelper();
        List<ChatLieu> listCL = new ArrayList<>();
        try {
            ResultSet rs = dbHelper.executeQuery(sqlQuery, params);
            while (rs.next()) {
                listCL.add(
                        new ChatLieu(
                                rs.getInt("id"),
                                rs.getString("maChatLieu"),
                                rs.getString("tenChatLieu")
                        )
                );
            }
            dbHelper.closeResultSet(rs);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCL;
    }

    public int add(ChatLieu chatLieu) {
        String sql = "INSERT INTO ChatLieu (MaChatLieu, TenChatLieu) VALUES (?, ?)";
        try {
            return dbHelper.executeUpdate(sql, chatLieu.getMaChatLieu(), chatLieu.getTenChatLieu());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int update(ChatLieu chatLieu) {
        String sql = "UPDATE ChatLieu SET TenChatLieu = ? WHERE MaChatLieu = ?";
        try {
            return dbHelper.executeUpdate(sql, chatLieu.getTenChatLieu(), chatLieu.getMaChatLieu());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public ArrayList<ChatLieu> getAll() {
        ArrayList<ChatLieu> list = new ArrayList<>();
        Connection conn = DatabaseHelper.getConnection();
        String sql = "SELECT ID, MaChatLieu, TenChatLieu FROM ChatLieu";
        try {
            Statement stm = conn.createStatement();
            ResultSet rs = stm.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String MaSp = rs.getString(2);
                String TenSP = rs.getString(3);
                ChatLieu chatLieu = new ChatLieu(id, MaSp, TenSP);
                list.add(chatLieu);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String generateMaChatLieu() {
        String sql = "SELECT MAX(CAST(SUBSTRING(MaChatLieu, 3, LENGTH(MaChatLieu) - 2) AS UNSIGNED)) FROM ChatLieu WHERE MaChatLieu LIKE 'CL%'";
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

        // Kiểm tra nếu ma là chuỗi rỗng hoặc null
        int nextId = 1;
        if (ma != null && !ma.isEmpty()) {
            nextId = Integer.parseInt(ma) + 1;
        }

        return String.format("CL%03d", nextId);
    }

    public int deleteByMa(String maChatLieu) {
        String sql = "DELETE FROM ChatLieu WHERE maChatLieu = ?";
        try {
            PreparedStatement ps = dbHelper.getConnection().prepareStatement(sql);
            ps.setString(1, maChatLieu);
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