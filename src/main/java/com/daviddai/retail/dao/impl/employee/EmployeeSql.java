
package com.daviddai.retail.dao.impl.employee;

public final class EmployeeSql {

    private EmployeeSql() {
    }

    private static final String SELECT = """
            SELECT id, code, full_name, phone, birth_date, role, gender,
                   address, username, password_hash, created_at, is_active
            FROM employee
            """;

    public static final String FIND_BY_ID = SELECT + "WHERE id = ?";

    public static final String FIND_BY_ID_FOR_UPDATE = SELECT + "WHERE id = ? FOR UPDATE";

    public static final String FIND_BY_USERNAME = SELECT + "WHERE username = ?";

    public static final String FIND_ALL = SELECT + "ORDER BY id";

    public static final String FIND_BY_ROLE = SELECT + "WHERE role = ? ORDER BY id";

    public static final String SEARCH = SELECT + """
            WHERE code ILIKE ?
               OR full_name ILIKE ?
               OR phone ILIKE ?
               OR username ILIKE ?
            ORDER BY id
            LIMIT ? OFFSET ?
            """;

    public static final String INSERT = """
            INSERT INTO employee (
                code, full_name, phone, birth_date, role, gender,
                address, username, password_hash, is_active
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            RETURNING id
            """;

    public static final String UPDATE = """
            UPDATE employee
            SET code = ?, full_name = ?, phone = ?, birth_date = ?,
                role = ?, gender = ?, address = ?, username = ?,
                password_hash = ?, is_active = ?
            WHERE id = ?
            """;

    public static final String DELETE = "DELETE FROM employee WHERE id = ?";

    public static final String SOFT_DELETE = "UPDATE employee SET is_active = FALSE WHERE id = ?";

    public static final String COUNT_SEARCH = """
            SELECT COUNT(*) FROM employee
            WHERE code ILIKE ? OR full_name ILIKE ? OR phone ILIKE ? OR username ILIKE ?
            """;

    public static final String EXISTS_BY_CODE = """
                SELECT 1 FROM employee WHERE code = ?
            """;
    public static final String EXISTS_BY_USERNAME = """
            SELECT EXISTS (
                SELECT 1 FROM employee WHERE username = ?
            )
            """;

    public static final String EXISTS_BY_PHONE = """
            SELECT EXISTS (
                SELECT 1 FROM employee WHERE phone = ?
            )
            """;
}
