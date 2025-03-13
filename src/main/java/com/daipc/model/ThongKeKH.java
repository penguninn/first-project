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
public class ThongKeKH {
    private String maKH;
    private String hoTen;
    private int soDonHang;
    private BigDecimal tongGiaTriHD;

    public ThongKeKH() {
    }

    public ThongKeKH(String maKH, String hoTen, int soDonHang, BigDecimal tongGiaTriHD) {
        this.maKH = maKH;
        this.hoTen = hoTen;
        this.soDonHang = soDonHang;
        this.tongGiaTriHD = tongGiaTriHD;
    }

    public String getMaKH() {
        return maKH;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getSoDonHang() {
        return soDonHang;
    }

    public void setSoDonHang(int soDonHang) {
        this.soDonHang = soDonHang;
    }

    public BigDecimal getTongGiaTriHD() {
        return tongGiaTriHD;
    }

    public void setTongGiaTriHD(BigDecimal tongGiaTriHD) {
        this.tongGiaTriHD = tongGiaTriHD;
    }
    
    
}
