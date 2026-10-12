package com.daviddai.retail.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductVariant {

    private Integer id;

    private String code;

    private int productId;

    private int colorId;

    private int sizeId;

    private int materialId;

    private int thicknessId;

    private Integer supplierId;

    private int stockQuantity = 0;

    private BigDecimal costPrice = BigDecimal.ZERO;

    private BigDecimal salePrice = BigDecimal.ZERO;

    private LocalDateTime createdAt;

    private boolean isActive = true;

    public ProductVariant() {
    }

    public ProductVariant(Integer id, String code, int productId, int colorId, int sizeId, int materialId,
            int thicknessId, Integer supplierId, int stockQuantity, BigDecimal costPrice, BigDecimal salePrice,
            LocalDateTime createdAt, boolean isActive) {
        this.id = id;
        this.code = code;
        this.productId = productId;
        this.colorId = colorId;
        this.sizeId = sizeId;
        this.materialId = materialId;
        this.thicknessId = thicknessId;
        this.supplierId = supplierId;
        this.stockQuantity = stockQuantity;
        this.costPrice = costPrice;
        this.salePrice = salePrice;
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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    public int getSizeId() {
        return sizeId;
    }

    public void setSizeId(int sizeId) {
        this.sizeId = sizeId;
    }

    public int getMaterialId() {
        return materialId;
    }

    public void setMaterialId(int materialId) {
        this.materialId = materialId;
    }

    public int getThicknessId() {
        return thicknessId;
    }

    public void setThicknessId(int thicknessId) {
        this.thicknessId = thicknessId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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
