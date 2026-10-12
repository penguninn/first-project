package com.daviddai.retail.model;

import java.time.LocalDateTime;

public class Size {

    private Integer id;

    private String code;

    private String name;

    private LocalDateTime createdAt;

    private boolean isActive = true;

    public Size() {
    }

    public Size(Integer id, String code, String name, LocalDateTime createdAt, boolean isActive) {
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

    public Size(Integer id, String maSize, String tenSize) {
        this.id = id;
        this.code = maSize;
        this.name = tenSize;
    }

    public String getMaSize() {
        return code;
    }

    public void setMaSize(String maSize) {
        this.code = maSize;
    }

    public String getTenSize() {
        return name;
    }

    public void setTenSize(String tenSize) {
        this.name = tenSize;
    }

    public Object[] getSize() {
        return new Object[] { id, code, name };
    }
}
