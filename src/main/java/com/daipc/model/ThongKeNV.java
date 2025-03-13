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
public class ThongKeNV {

    private String hoTen;
    private int tongDonHang;
    private BigDecimal tongGiaTri;
    private int soLuongKachHang;

    public ThongKeNV() {
    }

    public ThongKeNV(String hoTen, int tongDonHang, BigDecimal tongGiaTri, int soLuongKachHang) {
        this.hoTen = hoTen;
        this.tongDonHang = tongDonHang;
        this.tongGiaTri = tongGiaTri;
        this.soLuongKachHang = soLuongKachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public int getTongDonHang() {
        return tongDonHang;
    }

    public void setTongDonHang(int tongDonHang) {
        this.tongDonHang = tongDonHang;
    }

    public BigDecimal getTongGiaTri() {
        return tongGiaTri;
    }

    public void setTongGiaTri(BigDecimal tongGiaTri) {
        this.tongGiaTri = tongGiaTri;
    }

    public int getSoLuongKachHang() {
        return soLuongKachHang;
    }

    public void setSoLuongKachHang(int soLuongKachHang) {
        this.soLuongKachHang = soLuongKachHang;
    }

}
