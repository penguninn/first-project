/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author daipc
 */
public class Voucher {
    private int id;
    private String maVoucher;
    private BigDecimal giaTriVoucher;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private int soLuong;
    private String moTa;
    private int trangThai;

    public Voucher() {
    }

    public Voucher(int id, String maVoucher, BigDecimal giaTriVoucher, Date ngayBatDau, Date ngayKetThuc, int soLuong, String moTa) {
        this.id = id;
        this.maVoucher = maVoucher;
        this.giaTriVoucher = giaTriVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.moTa = moTa;
    }

    public Voucher(int id, String maVoucher, BigDecimal giaTriVoucher, Date ngayBatDau, Date ngayKetThuc, int soLuong, String moTa, int trangThai) {
        this.id = id;
        this.maVoucher = maVoucher;
        this.giaTriVoucher = giaTriVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }

    public Voucher(String maVoucher, BigDecimal giaTriVoucher, Date ngayBatDau, Date ngayKetThuc, int soLuong, String moTa, int trangThai) {
        this.maVoucher = maVoucher;
        this.giaTriVoucher = giaTriVoucher;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.soLuong = soLuong;
        this.moTa = moTa;
        this.trangThai = trangThai;
    }
    
    
    
    public Object[] getObj() {
        return new Object[]{id, maVoucher, giaTriVoucher, ngayBatDau, ngayKetThuc, soLuong, moTa};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaVoucher() {
        return maVoucher;
    }

    public void setMaVoucher(String maVoucher) {
        this.maVoucher = maVoucher;
    }

    public BigDecimal getGiaTriVoucher() {
        return giaTriVoucher;
    }

    public void setGiaTriVoucher(BigDecimal giaTriVoucher) {
        this.giaTriVoucher = giaTriVoucher;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    
    
    
}
