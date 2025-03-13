/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import com.daipc.enumm.TrangThai;

/**
 *
 * @author DaiPc
 */
public class SanPham {
    private int id;
    private String maSP;
    private String tenSP;
    private String moTa;
    private String hienThi;

    public SanPham() {
    }

    public SanPham(String maSP, String tenSP, String moTa) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
    }

    public SanPham(int id, String maSP, String tenSP, String moTa, String hienThi) {
        this.id = id;
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.moTa = moTa;
        this.hienThi = hienThi;
    }
    
    public Object[] getSanPham() {
        return new Object[] {maSP, tenSP, moTa};
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaSP() {
        return maSP;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public String getTenSP() {
        return tenSP;
    }

    public void setTenSP(String tenSP) {
        this.tenSP = tenSP;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHienThi() {
        return hienThi;
    }

    public void setHienThi(String hienThi) {
        this.hienThi = hienThi;
    }
    
    
}
