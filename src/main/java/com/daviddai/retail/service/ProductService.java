package com.daviddai.retail.service;

import java.util.List;

import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.PageResult;
import com.daviddai.retail.model.dto.result.ProductResult;

public interface ProductService {

    ProductResult findById(int id);

    List<ProductResult> findAll();

    List<ProductResult> search(String keyword);

    PageResult<ProductResult> search(String keyword, int page, int pageSize);

    int create(ProductCommand command);

    void update(int id, ProductCommand command);

    void delete(int id);
}
