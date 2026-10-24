package com.daviddai.retail.model.dto.result;

import java.time.LocalDateTime;

public record ProductResult(
        Integer id,
        String code,
        String name,
        String description,
        LocalDateTime createdAt,
        boolean isActive) {

    public Integer id() {
        return id;
    }

    public String code() {
        return code;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    public LocalDateTime createdAt() {
        return createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

}
