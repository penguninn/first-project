package com.daviddai.retail.dao;

import java.util.List;
import java.util.Optional;

import com.daviddai.retail.model.Product;

public interface ProductDao {

    Optional<Product> findById(int id);

    Optional<Product> findByIdForUpdate(int id);

    List<Product> findAll();

    List<Product> search(String keyword);

    List<Product> search(String keyword, int page, int size);

    int countSearch(String keyword);

    boolean existsByCode(String code);

    int insert(Product product);

    void update(Product product);

    void delete(int id);

    void softDelete(int id);
}
