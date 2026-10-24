package com.daviddai.retail.service.impl;

import java.util.List;

import com.daviddai.retail.dao.ProductDao;
import com.daviddai.retail.exception.BusinessException;
import com.daviddai.retail.exception.NotFoundException;
import com.daviddai.retail.mapper.ProductMapper;
import com.daviddai.retail.model.Product;
import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.PageResult;
import com.daviddai.retail.model.dto.result.ProductResult;
import com.daviddai.retail.service.ProductService;
import com.daviddai.retail.ui.UiUtils;

public class ProductServiceImpl implements ProductService {

    private final ProductDao dao;

    public ProductServiceImpl(ProductDao dao) {
        this.dao = dao;
    }

    @Override
    public ProductResult findById(int id) {
        return ProductMapper.mapEntityToResult(dao.findById(id)
                .orElseThrow(() -> NotFoundException.of("Sản phẩm", id)));
    }

    @Override
    public List<ProductResult> findAll() {
        return dao.findAll().stream()
                .map(ProductMapper::mapEntityToResult).toList();
    }

    @Override
    public List<ProductResult> search(String keyword) {
        List<ProductResult> items = dao.search(keyword).stream()
                .map(ProductMapper::mapEntityToResult).toList();

        return items;
    }

    @Override
    public PageResult<ProductResult> search(String keyword, int page, int size) {
        if (page < 1) {
            page = 1;
        }
        if (size < 1 || size > 100) {
            size = 20;
        }
        List<ProductResult> items = dao.search(keyword, page, size).stream()
                .map(ProductMapper::mapEntityToResult).toList();

        return new PageResult<ProductResult>(items, page, size, dao.countSearch(keyword));
    }

    @Override
    public int create(ProductCommand command) {
        System.out.println("create");
        Product p = ProductMapper.mapCommandToEntity(command);
        p.setCode(UiUtils.gennerateCodeFromName(p.getName()));
        if (dao.existsByCode(p.getCode())) {
            throw new BusinessException("Mã sản phẩm đã tồn tại: " + p.getCode());
        }
        return dao.insert(p);
    }

    @Override
    public void update(int id, ProductCommand command) {
        System.out.println("start");
        Product existing = dao.findById(id)
                .orElseThrow(() -> NotFoundException.of("Sản phẩm", id));
        String code = command.code() == null || command.code().isBlank()
                ? existing.getCode()
                : command.code();
        if (!code.equals(existing.getCode()) && dao.existsByCode(code)) {
            throw new BusinessException("Mã sản phẩm đã tồn tại: " + code);
        }
        Product p = ProductMapper.mapCommandToEntity(command);
        p.setId(id);
        p.setCode(code);
        dao.update(p);
        System.out.println("done");
    }

    @Override
    public void delete(int id) {
        dao.findById(id)
                .orElseThrow(() -> NotFoundException.of("Sản phẩm", id));
        dao.delete(id);
    }
}
