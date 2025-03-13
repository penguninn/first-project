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
public class HoaDonCho {
    private int id;
    private String maHD;
    private String tenKhachHang;
    private String nguoiTao;
    private String voucher;
    private String thongTinVoucher;
    private BigDecimal thanhToan;
    private String ngayTao;
    private int TrangThai;
    private BigDecimal tong;
    private String SDT;
    private String hinhThucTT;
    private String ghiChu;
    
    public HoaDonCho() {
    }

    public HoaDonCho(int id, String maHD, String tenKhachHang, String nguoiTao, String voucher, BigDecimal thanhToan, String hinhThucTT, String ngayTao, int TrangThai, BigDecimal tong, String SDT, String ghiChu, String thongTinVoucher) {
        this.id = id;
        this.maHD = maHD;
        this.tenKhachHang = tenKhachHang;
        this.nguoiTao = nguoiTao;
        this.voucher = voucher;
        this.thanhToan = thanhToan;
        this.ngayTao = ngayTao;
        this.TrangThai = TrangThai;
        this.tong = tong;
        this.SDT = SDT;
        this.hinhThucTT = hinhThucTT;
        this.ghiChu = ghiChu;
        this.thongTinVoucher = thongTinVoucher;
    }

    public String getThongTinVoucher() {
        return thongTinVoucher;
    }

    public void setThongTinVoucher(String thongTinVoucher) {
        this.thongTinVoucher = thongTinVoucher;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }
    
    public String getHinhThucTT() {
        return hinhThucTT;
    }

    public void setHinhThucTT(String hinhThucTT) {
        this.hinhThucTT = hinhThucTT;
    }
    
    
    public Object[] getHDC() {
        return new Object[]{maHD, tenKhachHang, nguoiTao, ngayTao, tong};
    }
    
    public String getMaHD() {
        return maHD;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public String getTenKhachHang() {
        return tenKhachHang;
    }

    public void setTenKhachHang(String tenKhachHang) {
        this.tenKhachHang = tenKhachHang;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(int TrangThai) {
        this.TrangThai = TrangThai;
    }

    public BigDecimal getTong() {
        return tong;
    }

    public void setTong(BigDecimal tong) {
        this.tong = tong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public BigDecimal getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(BigDecimal thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    
}
