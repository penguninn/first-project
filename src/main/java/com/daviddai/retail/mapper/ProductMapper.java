package com.daviddai.retail.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.daviddai.retail.model.Product;
import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.ProductResult;

public final class ProductMapper {

    public static Product mapRsToEntity(ResultSet rs) throws SQLException {
        Product p = new Product();
        p.setId(rs.getInt("id"));
        p.setCode(rs.getString("code"));
        p.setName(rs.getString("name"));
        p.setActive(rs.getBoolean("isActive"));
        p.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        p.setDescription(rs.getString("description"));
        return p;
    }

    public static ProductResult mapEntityToResult(Product product) {
        return new ProductResult(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getCreatedAt(),
                product.isActive());
    }

    public static Product mapCommandToEntity(ProductCommand command) {
        Product p = new Product();
        p.setName(command.name());
        p.setDescription(command.description());
        p.setActive(command.isActive());
        return p;
    }
}
