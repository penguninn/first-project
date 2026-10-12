package com.daviddai.retail.model.dto.command;

public record ProductCommand(
        String name, String description, boolean isActive) {
}
