/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author DaiPc
 */
public class ThongKeSP {
    private String maSP;
    private String tenSP;
    private int soLuongBan;
    private int soLuongTonKho;

    public ThongKeSP() {
    }

    public ThongKeSP(String maSP, String tenSP, int soLuongBan, int soLuongTonKho) {
        this.maSP = maSP;
        this.tenSP = tenSP;
        this.soLuongBan = soLuongBan;
        this.soLuongTonKho = soLuongTonKho;
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

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getSoLuongTonKho() {
        return soLuongTonKho;
    }

    public void setSoLuongTonKho(int soLuongTonKho) {
        this.soLuongTonKho = soLuongTonKho;
    }
    
    
}
