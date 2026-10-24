package com.daviddai.retail.config;

import org.junit.jupiter.api.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test kết nối DB. Yêu cầu PostgreSQL đang chạy + DB DuAn1_Final tồn tại.
 * Nếu DB không sẵn sàng → test fail (đúng ý đồ).
 */
class DataSourceFactoryTest {

    @Test
    void should_connect_to_postgres() throws Exception {
        AppConfig config = AppConfig.load();
        DataSource ds = DataSourceFactory.create(config);

        try (Connection conn = ds.getConnection();
                Statement st = conn.createStatement();
                ResultSet rs = st.executeQuery("SELECT 1")) {

            assertTrue(conn.isValid(2), "Connection phải hợp lệ");
            assertTrue(rs.next(), "Query SELECT 1 phải có kết quả");
            assertEquals(1, rs.getInt(1));
        } finally {
            if (ds instanceof AutoCloseable ac)
                ac.close();
        }
    }
}
