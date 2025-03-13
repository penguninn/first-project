/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.math.BigDecimal;

/**
 *
 * @author DaiPc
 */
public class GioHang {
    private int id;
    private String maSPCT;
    private String tenSPCT;
    private BigDecimal donGia;
    private int soLuong;
    private BigDecimal trangThai;
    private BigDecimal thanhTien;

    public GioHang() {
    }
    
    

    public GioHang(int id, String maSPCT, String tenSPCT, BigDecimal donGia, int soLuong, BigDecimal thanhTien, BigDecimal trangThai) {
        this.id = id;
        this.maSPCT = maSPCT;
        this.tenSPCT = tenSPCT;
        this.donGia = donGia;
        this.soLuong = soLuong;
        this.thanhTien = thanhTien;
        this.trangThai = trangThai;
    }

    public GioHang(String maCTSP, String tenSPCT, BigDecimal donGia, int soLuong) {
        this.maSPCT = maCTSP;
        this.tenSPCT = tenSPCT;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = this.donGia.multiply(BigDecimal.valueOf(soLuong));
    }

    public Object[] getGioHang() {
        return new Object[]{maSPCT, tenSPCT, donGia, soLuong, thanhTien};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    public String getMaCTSP() {
        return maSPCT;
    }

    public void setMaCTSP(String MaCTSP) {
        this.maSPCT = MaCTSP;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public BigDecimal isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(BigDecimal trangThai) {
        this.trangThai = trangThai;
    }

    public String getTenSPCT() {
        return tenSPCT;
    }

    public void setTenSPCT(String tenSPCT) {
        this.tenSPCT = tenSPCT;
    }

    public BigDecimal getTongTien() {
        return thanhTien;
    }

    public void setTongTien(BigDecimal tongTien) {
        this.thanhTien = tongTien;
    }

    public String getMaSPCT() {
        return maSPCT;
    }

    public void setMaSPCT(String maSPCT) {
        this.maSPCT = maSPCT;
    }

    public BigDecimal getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(BigDecimal thanhTien) {
        this.thanhTien = thanhTien;
    }

}
