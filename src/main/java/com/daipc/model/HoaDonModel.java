/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author admin
 */
public class HoaDonModel {

    
    private int id;
    private String maHD;
    private String tenKH;
    private String sdt;
    private BigDecimal donGia;
    private String trangThai;
    private Date ngayTao;
    private String tenNguoiTao;
    

    // Getters và Setters

    public HoaDonModel() {
    }

    public HoaDonModel(int id, String maHD, String tenKH, String sdt, BigDecimal donGia, String trangThai, Date ngayTao, String tenNguoiTao) {
        this.id = id;
        this.maHD = maHD;
        this.tenKH = tenKH;
        this.sdt = sdt;
        this.donGia = donGia;
        this.trangThai = trangThai;
        this.ngayTao = ngayTao;
        this.tenNguoiTao = tenNguoiTao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public BigDecimal getDonGia() {
        return donGia;
    }

    public void setDonGia(BigDecimal donGia) {
        this.donGia = donGia;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public Date getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(Date ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTenNguoiTao() {
        return tenNguoiTao;
    }

    public void setTenNguoiTao(String tenNguoiTao) {
        this.tenNguoiTao = tenNguoiTao;
    }

   

    @Override
    public String toString() {
        return "HoaDonModel{" +
                "maHD=" + maHD +
                ", tenKH='" + tenKH + '\'' +
                ", sdt='" + sdt + '\'' +
                ", donGia=" + donGia +
                ", trangThai='" + trangThai + '\'' +
                ", ngayTao=" + ngayTao +
                ", tenNguoiTao='" + tenNguoiTao + '\'' +
                '}';
    }
}

    

