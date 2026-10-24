package com.daviddai.retail.ui.product;

import java.awt.Component;
import java.util.List;
import java.util.function.Consumer;

import com.daviddai.retail.exception.AsyncException;
import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.ProductResult;
import com.daviddai.retail.service.ProductService;
import com.daviddai.retail.ui.component.toast.Toast;

public class ProductPresenter {

    private final ProductView productView;
    private final ProductService productService;

    public ProductPresenter(ProductView productView, ProductService productService) {
        this.productView = productView;
        this.productService = productService;

        productView.setOnAddAction(this::handleAddRequest);
        productView.setOnSearchAction(this::handleSearchRequest);
        productView.setOnEditItemAction(this::handleEditRequest);
        productView.setOnDeleteItemAction(this::handleDeleteRequest);

        refreshData();
    }

    private void handleAddRequest() {
        productView.openForm(null, this::handleSaveForm);
    }

    private void handleEditRequest(ProductResult result) {
        ProductCommand command = new ProductCommand(
                result.id(),
                result.code(),
                result.name(),
                result.description(),
                result.isActive());
        productView.openForm(command, this::handleSaveForm);
    }

    private void handleDeleteRequest(ProductResult result) {
        if (productView.confirmDelete()) {
            productService.delete(result.id());
            refreshData();
        }
    }

    private void handleSearchRequest() {
        String keyword = productView.getSearchKeyword();
        List<ProductResult> results = productService.search(keyword);
        productView.setData(results);
    }

    private void handleSaveForm(ProductCommand command, Consumer<String> onResult) {
        AsyncException.run(
                () -> {
                    if (command.id() == null) {
                        productService.create(command);
                    } else {
                        productService.update(command.id(), command);
                    }
                    return null;
                },
                result -> {
                    onResult.accept(null);
                    refreshData();
                    Toast.success((Component) productView, "Lưu sản phẩm thành công!");
                },
                e -> {
                    onResult.accept(e.getMessage());
                },
                () -> {
                });
    }

    private void refreshData() {
        productView.setData(productService.findAll());
    }

}
