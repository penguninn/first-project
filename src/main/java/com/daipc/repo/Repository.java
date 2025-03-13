
package com.daipc.repo;

import com.daipc.model.SanPham;
import java.util.ArrayList;
import java.util.List;

public interface Repository<T> {
    List<T> select(String sqlQuery, Object... params);
    ArrayList<SanPham> getAll();
}
