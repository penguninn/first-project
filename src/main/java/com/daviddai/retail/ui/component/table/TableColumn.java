package com.daviddai.retail.ui.component.table;

import java.util.function.Function;

public record TableColumn<T>(
        String name, Function<T, Object> extractor, Class<?> type, int preferredWidth) {

    public TableColumn(String name, Function<T, Object> extractor) {
        this(name, extractor, Object.class, -1);
    }

    public TableColumn(String name, Function<T, Object> extractor, Class<?> type) {
        this(name, extractor, type, -1);
    }

    public TableColumn(String name, Function<T, Object> extractor, int width) {
        this(name, extractor, Object.class, width);
    }
}
