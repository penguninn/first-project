package com.daviddai.retail.ui.product;

import java.util.List;
import java.util.function.Consumer;

import com.daviddai.retail.model.dto.command.ProductCommand;
import com.daviddai.retail.model.dto.result.ProductResult;

public interface ProductView {

    // Action
    void setOnAddAction(Runnable action);

    void setOnSearchAction(Runnable action);

    void setOnEditItemAction(Consumer<ProductResult> action);

    void setOnDeleteItemAction(Consumer<ProductResult> action);

    // Api for presenter
    void setData(List<ProductResult> products);

    String getSearchKeyword();

    boolean confirmDelete();

    void openForm(ProductCommand initial, SaveHandler saveHandler);
}
