package com.daviddai.retail.model;

import static java.sql.Date.valueOf;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class Voucher {

    private Integer id;

    private String code;

    private String discountType = "AMOUNT";

    private BigDecimal discountValue;

    private BigDecimal minOrderAmount = BigDecimal.ZERO;

    private LocalDate startDate;

    private LocalDate endDate;

    private int quantity = 0;

    private String description;

    private boolean isActive = true;

    public Voucher() {
    }

    public Voucher(Integer id, String code, String discountType, BigDecimal discountValue, BigDecimal minOrderAmount,
            LocalDate startDate, LocalDate endDate, int quantity, String description, boolean isActive) {
        this.id = id;
        this.code = code;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.minOrderAmount = minOrderAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quantity = quantity;
        this.description = description;
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

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(BigDecimal minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    private transient int legacyScheduleStatus;

    public Voucher(int id, String maVoucher, BigDecimal giaTriVoucher,
            Date ngayBatDau, Date ngayKetThuc,
            int soLuong, String moTa) {
        this.id = id;
        this.code = maVoucher;
        this.discountValue = giaTriVoucher;
        setNgayBatDau(ngayBatDau);
        setNgayKetThuc(ngayKetThuc);
        this.quantity = soLuong;
        this.description = moTa;
    }

    public Voucher(int id, String maVoucher, BigDecimal giaTriVoucher,
            Date ngayBatDau, Date ngayKetThuc,
            int soLuong, String moTa, int trangThai) {
        this(id, maVoucher, giaTriVoucher, ngayBatDau, ngayKetThuc, soLuong, moTa);
        this.legacyScheduleStatus = trangThai;
    }

    public Voucher(String maVoucher, BigDecimal giaTriVoucher,
            Date ngayBatDau, Date ngayKetThuc,
            int soLuong, String moTa, int trangThai) {
        this(0, maVoucher, giaTriVoucher, ngayBatDau, ngayKetThuc, soLuong, moTa, trangThai);
        this.id = null;
    }

    public String getMaVoucher() {
        return code;
    }

    public void setMaVoucher(String maVoucher) {
        this.code = maVoucher;
    }

    public BigDecimal getGiaTriVoucher() {
        return discountValue;
    }

    public void setGiaTriVoucher(BigDecimal giaTriVoucher) {
        this.discountValue = giaTriVoucher;
    }

    public Date getNgayBatDau() {
        return startDate == null ? null : valueOf(startDate);
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.startDate = toLocalDate(ngayBatDau);
    }

    public Date getNgayKetThuc() {
        return endDate == null ? null : valueOf(endDate);
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.endDate = toLocalDate(ngayKetThuc);
    }

    public int getSoLuong() {
        return quantity;
    }

    public void setSoLuong(int soLuong) {
        this.quantity = soLuong;
    }

    public String getMoTa() {
        return description;
    }

    public void setMoTa(String moTa) {
        this.description = moTa;
    }

    public int getTrangThai() {
        return legacyScheduleStatus;
    }

    public void setTrangThai(int trangThai) {
        this.legacyScheduleStatus = trangThai;
    }

    public Object[] getObj() {
        return new Object[] { id, code, discountValue, getNgayBatDau(), getNgayKetThuc(), quantity, description };
    }

    private static LocalDate toLocalDate(Date value) {
        if (value == null)
            return null;
        return Instant.ofEpochMilli(value.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
