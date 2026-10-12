package com.daviddai.retail.model;

import java.math.BigDecimal;

public class InvoiceItem {

    private Integer id;

    private int invoiceId;

    private int variantId;

    private BigDecimal unitPrice;

    private BigDecimal costPrice;

    private int quantity;

    public InvoiceItem() {
    }

    public InvoiceItem(Integer id, int invoiceId, int variantId, BigDecimal unitPrice, BigDecimal costPrice,
            int quantity) {
        this.id = id;
        this.invoiceId = invoiceId;
        this.variantId = variantId;
        this.unitPrice = unitPrice;
        this.costPrice = costPrice;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getVariantId() {
        return variantId;
    }

    public void setVariantId(int variantId) {
        this.variantId = variantId;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
