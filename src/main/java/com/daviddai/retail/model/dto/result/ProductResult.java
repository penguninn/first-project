package com.daviddai.retail.model.dto.result;

import java.time.LocalDateTime;

public record ProductResult(
        Integer id,
        String code,
        String name,
        String description,
        LocalDateTime createdAt,
        boolean isActive) {

}
