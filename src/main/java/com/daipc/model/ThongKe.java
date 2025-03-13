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
public class ThongKe {
    private int donHang;
    private int donThanhCong;
    private int donHuy;
    private BigDecimal doanhThu;
    private BigDecimal chiPhi;
    private BigDecimal loiNhuan;

    public ThongKe() {
    }
    
    public ThongKe(int donHang, BigDecimal doanhThu, BigDecimal chiPhi, BigDecimal loiNhuan) {
        this.donHang = donHang;
        this.doanhThu = doanhThu;
        this.chiPhi = chiPhi;
        this.loiNhuan = loiNhuan;
    }

    public ThongKe(int donHang, int donThanhCong, int donHuy, BigDecimal doanhThu, BigDecimal chiPhi, BigDecimal loiNhuan) {
        this.donHang = donHang;
        this.donThanhCong = donThanhCong;
        this.donHuy = donHuy;
        this.doanhThu = doanhThu;
        this.chiPhi = chiPhi;
        this.loiNhuan = loiNhuan;
    }

    public int getDonThanhCong() {
        return donThanhCong;
    }

    public void setDonThanhCong(int donThanhCong) {
        this.donThanhCong = donThanhCong;
    }

    public int getDonHuy() {
        return donHuy;
    }

    public void setDonHuy(int donHuy) {
        this.donHuy = donHuy;
    }

    public int getDonHang() {
        return donHang;
    }

    public void setDonHang(int donHang) {
        this.donHang = donHang;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public BigDecimal getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(BigDecimal chiPhi) {
        this.chiPhi = chiPhi;
    }

    public BigDecimal getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(BigDecimal loiNhuan) {
        this.loiNhuan = loiNhuan;
    }
    
    
}
