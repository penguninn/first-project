package com.daviddai.retail.model.dto.command;

public record ProductCommand(
        Integer id, String code,
        String name, String description, boolean isActive) {

}
