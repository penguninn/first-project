/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.math.BigDecimal;

/**
 *
 * @author admin
 */
public class ChiTietSP {
    private int id;
     private String maCTSP;
    private String MaSP;
    private String tenSP;
    private String tenSPCT;
    private BigDecimal giaBan;
    private BigDecimal giaNhap;
    private int soLuong;
    private String tenNhaCungCap;
    private String tenMauSac;
    private String tenSize;
    private String tenChatLieu;
    private String tenDoDay;
    private String trangThai;

    public ChiTietSP() {
    }

    public ChiTietSP(int id, String maCTSP, String MaSP, String tenSP, String tenSPCT, BigDecimal giaBan, BigDecimal giaNhap, int soLuong, String tenNhaCungCap, String tenMauSac, String tenSize, String tenChatLieu, String tenDoDay, String trangThai) {
        this.id = id;
        this.maCTSP = maCTSP;
        this.MaSP = MaSP;
        this.tenSP = tenSP;
        this.tenSPCT = tenSPCT;
        this.giaBan = giaBan;
        this.giaNhap = giaNhap;
        this.soLuong = soLuong;
        this.tenNhaCungCap = tenNhaCungCap;
        this.tenMauSac = tenMauSac;
        this.tenSize = tenSize;
        this.tenChatLieu = tenChatLieu;
        this.tenDoDay = tenDoDay;
        this.trangThai = trangThai;
    }

    public ChiTietSP(int id, String maCTSP, String tenSPCT, BigDecimal giaBan, String tenMauSac, String tenSize, String tenChatLieu, String tenDoDay, String tenNhaCungCap , int soLuong) {
        this.id = id;
        this.maCTSP = maCTSP;
        this.tenSPCT = tenSPCT;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
        this.tenNhaCungCap = tenNhaCungCap;
        this.tenMauSac = tenMauSac;
        this.tenSize = tenSize;
        this.tenChatLieu = tenChatLieu;
        this.tenDoDay = tenDoDay;
    }
    
    public Object[] getSPCT() {
        return new Object[] {maCTSP, tenSPCT, giaBan, tenMauSac, tenSize, tenChatLieu, tenDoDay, soLuong};
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaCTSP() {
        return maCTSP;
    }

    public void setMaCTSP(String maCTSP) {
        this.maCTSP = maCTSP;
    }

    public String getMaSP() {
        return MaSP;
    }

    public void setMaSP(String MaSP) {
        this.MaSP = MaSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getTenSPCT() {
        return tenSPCT;
    }

    public void setTenSPCT(String tenSPCT) {
        this.tenSPCT = tenSPCT;
    }

    public BigDecimal getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(BigDecimal giaBan) {
        this.giaBan = giaBan;
    }

    public BigDecimal getGiaNhap() {
        return giaNhap;
    }

    public void setGiaNhap(BigDecimal giaNhap) {
        this.giaNhap = giaNhap;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }

    public String getTenDoDay() {
        return tenDoDay;
    }

    public void setTenDoDay(String tenDoDay) {
        this.tenDoDay = tenDoDay;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    

   
    
}
