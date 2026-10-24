package com.daviddai.retail.ui.product;

import java.util.function.Consumer;

import com.daviddai.retail.model.dto.command.ProductCommand;

@FunctionalInterface
public interface SaveHandler {

    void save(ProductCommand command, Consumer<String> onResult);
}
