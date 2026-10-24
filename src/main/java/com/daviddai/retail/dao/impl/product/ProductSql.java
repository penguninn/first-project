package com.daviddai.retail.dao.impl.product;

public final class ProductSql {

    private ProductSql() {
    }

    private static final String SELECT = """
            SELECT id, code, name, description, created_at, is_active AS "isActive"
            FROM product
            """;

    public static final String FIND_BY_ID = SELECT + "WHERE id = ?";

    public static final String FIND_BY_ID_FOR_UPDATE = SELECT + "WHERE id = ? FOR UPDATE";

    public static final String FIND_ALL = SELECT + "ORDER BY id";

    public static final String SEARCH = SELECT + """
            WHERE code ILIKE ?
               OR name ILIKE ?
               OR description ILIKE ?
            """;

    public static final String SEARCH_PAGINATION = SELECT + """
            WHERE code ILIKE ?
               OR name ILIKE ?
               OR description ILIKE ?
            ORDER BY id
            LIMIT ? OFFSET ?
            """;

    public static final String INSERT = """
            INSERT INTO product (
                code, name, description, is_active
            ) VALUES (?, ?, ?, ?)
            RETURNING id
            """;

    public static final String UPDATE = """
            UPDATE product
            SET code = ?, name = ?, description = ?, is_active = ?
            WHERE id = ?
            """;

    public static final String DELETE = "DELETE FROM product WHERE id = ?";

    public static final String SOFT_DELETE = "UPDATE product SET is_active = FALSE WHERE id = ?";

    public static final String COUNT_SEARCH = """
            SELECT COUNT(*) FROM product
            WHERE code ILIKE ? OR name ILIKE ? OR description ILIKE ?
            """;

    public static final String EXISTS_BY_CODE = """
            SELECT EXISTS (
                SELECT 1 FROM product WHERE code = ?
            )
            """;
}
