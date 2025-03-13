/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author daipc
 */
public class KhachHang {

    private int id;
    private String maKhachHang;
    private String hoTen;
    private boolean gioiTinh;
    private String soDT;
    private String diaChi;
    private String ngayTao;
    private String ngaySua;
    private String nguoiTao;
    private String nguoiSua;

    private Boolean trangThai;


    public KhachHang() {
    }

    public KhachHang(int id, String maKhachHang, String hoTen, boolean gioiTinh, String soDT, String diaChi, String ngayTao, String ngaySua, String nguoiTao, String nguoiSua) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
        this.ngaySua = ngaySua;
        this.nguoiTao = nguoiTao;
        this.nguoiSua = nguoiSua;
    }

    public KhachHang(int id, String maKhachHang, String hoTen, boolean gioiTinh, String soDT, String diaChi, String ngayTao, String nguoiTao) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
        this.nguoiTao = nguoiTao;
    }


    public KhachHang(int id, String maKhachHang, String hoTen, boolean gioiTinh, String soDT, String diaChi, String ngayTao, Boolean trangThai) {
        this.id = id;
        this.maKhachHang = maKhachHang;
        this.hoTen = hoTen;
        this.gioiTinh = gioiTinh;
        this.soDT = soDT;
        this.diaChi = diaChi;
        this.ngayTao = ngayTao;
        this.trangThai = trangThai;
    }

    
    public Boolean getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getSoDT() {
        return soDT;
    }

    public void setSoDT(String soDT) {
        this.soDT = soDT;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getNgaySua() {
        return ngaySua;
    }

    public void setNgaySua(String ngaySua) {
        this.ngaySua = ngaySua;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getNguoiSua() {
        return nguoiSua;
    }

    public void setNguoiSua(String nguoiSua) {
        this.nguoiSua = nguoiSua;
    }

     private int tienThua;
    private int tongTienTra;
    private int tongGTHD;

    public int getTongGTHD() {
        return tongGTHD;
    }

    public void setTongGTHD(int tongGTHD) {
        this.tongGTHD = tongGTHD;
    }

    public int getTienThua() {
        return tienThua;
    }

    public void setTienThua(int tienThua) {
        this.tienThua = tienThua;
    }

    public int getTongTienTra() {
        return tongTienTra;
    }

    public void setTongTienTra(int tongTienTra) {
        this.tongTienTra = tongTienTra;
    }
}
