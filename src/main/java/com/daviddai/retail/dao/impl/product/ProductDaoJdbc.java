package com.daviddai.retail.dao.impl.product;

import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import com.daviddai.retail.dao.ProductDao;
import com.daviddai.retail.dao.impl.AbstractJdbcDao;
import com.daviddai.retail.exception.DataAccessException;
import com.daviddai.retail.mapper.ProductMapper;
import com.daviddai.retail.mapper.RowMapper;
import com.daviddai.retail.model.Product;

public class ProductDaoJdbc extends AbstractJdbcDao implements ProductDao {

    private static final RowMapper<Product> PRODUCT_MAPPER = ProductMapper::mapRsToEntity;

    public ProductDaoJdbc(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Optional<Product> findById(int id) {
        return queryOne(ProductSql.FIND_BY_ID, PRODUCT_MAPPER, id);
    }

    @Override
    public Optional<Product> findByIdForUpdate(int id) {
        return queryOne(ProductSql.FIND_BY_ID_FOR_UPDATE, PRODUCT_MAPPER, id);
    }

    @Override
    public List<Product> findAll() {
        return queryList(ProductSql.FIND_ALL, PRODUCT_MAPPER);
    }

    @Override
    public List<Product> search(String keyword) {
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        return queryList(ProductSql.SEARCH, PRODUCT_MAPPER,
                like, like, like);
    }

    @Override
    public List<Product> search(String keyword, int page, int size) {
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        int offset = Math.max(0, (page - 1) * size);
        return queryList(ProductSql.SEARCH_PAGINATION, PRODUCT_MAPPER,
                like, like, like, size, offset);
    }

    @Override
    public int insert(Product p) {
        return queryOne(ProductSql.INSERT, rs -> rs.getInt("id"),
                p.getCode(), p.getName(), p.getDescription(), p.isActive())
                .orElseThrow(() -> new DataAccessException(
                        "Không nhận được ID sản phẩm sau khi thêm"));
    }

    @Override
    public void update(Product p) {
        update(ProductSql.UPDATE,
                p.getCode(), p.getName(), p.getDescription(), p.isActive(), p.getId());
    }

    @Override
    public void delete(int id) {
        update(ProductSql.DELETE, id);
    }

    @Override
    public void softDelete(int id) {
        update(ProductSql.SOFT_DELETE, id);
    }

    @Override
    public int countSearch(String keyword) {
        String like = "%" + (keyword == null ? "" : keyword.trim()) + "%";
        return queryOne(ProductSql.COUNT_SEARCH, rs -> rs.getInt(1),
                like, like, like)
                .orElse(0);
    }

    @Override
    public boolean existsByCode(String code) {
        return queryOne(ProductSql.EXISTS_BY_CODE, rs -> rs.getBoolean(1), code)
                .orElse(false);
    }
}
