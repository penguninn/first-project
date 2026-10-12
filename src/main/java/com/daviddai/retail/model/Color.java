package com.daviddai.retail.model;

import java.time.LocalDateTime;

public class Color {

    private Integer id;

    private String code;

    private String name;

    private LocalDateTime createdAt;

    private boolean isActive = true;

    public Color() {
    }

    public Color(Integer id, String code, String name, LocalDateTime createdAt, boolean isActive) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.createdAt = createdAt;
        this.isActive = isActive;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
}
