/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;
/**
 *
 * @author DaiPc
 */
public class HoaDon {
    private int id;
    private String maHD;
    private int idKhachHang;
    private int idNhanVien;
    private int idVoucher;
    private double tongGiaTriHoaDon;
    private int thanhToan;
    private String ngayTao;
    private int trangThai;

    public HoaDon(int id, String maHD, int idKhachHang, int idNhanVien, int idVoucher, double tongGiaTriHoaDon, int thanhToan, String ngayTao, int trangThai) {
        this.id = id;
        this.maHD = maHD;
        this.idKhachHang = idKhachHang;
        this.idNhanVien = idNhanVien;
        this.idVoucher = idVoucher;
        this.tongGiaTriHoaDon = tongGiaTriHoaDon;
        this.thanhToan = thanhToan;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }
    
    public HoaDon() {
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

    public int getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(int idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public int getIdNhanVien() {
        return idNhanVien;
    }

    public void setIdNhanVien(int idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public int getIdVoucher() {
        return idVoucher;
    }

    public void setIdVoucher(int idVoucher) {
        this.idVoucher = idVoucher;
    }

    public double getTongGiaTriHoaDon() {
        return tongGiaTriHoaDon;
    }

    public void setTongGiaTriHoaDon(double tongGiaTriHoaDon) {
        this.tongGiaTriHoaDon = tongGiaTriHoaDon;
    }

    public int getThanhToan() {
        return thanhToan;
    }

    public void setThanhToan(int thanhToan) {
        this.thanhToan = thanhToan;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
