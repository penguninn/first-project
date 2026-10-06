
package com.daviddai.retail.repo;

import com.daviddai.retail.model.SanPham;
import java.util.ArrayList;
import java.util.List;

public interface Repository<T> {
    List<T> select(String sqlQuery, Object... params);
    ArrayList<SanPham> getAll();
}
