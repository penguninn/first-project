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
public class SPCT {
    private int ID ;
    private String MaSPCT ;
    private int SoLuong ;
    private int IdSanPham ;
    private int IdMauSac ;
    private int IdSize ;
    private int IdChatLieu ;
    private int IdDoDay ;
    private int IdNhaCungCap; 
    private String TenSPCT ;
    private Date NgayTao ;
    private Date NgaySua ;
    private BigDecimal GiaBan ;
     private BigDecimal GiaNhap ;
    private String Anh ;
    private String TrangThai ;

    public SPCT() {
    }

    public SPCT(int ID, String MaSPCT, int SoLuong, int IdSanPham, int IdMauSac, int IdSize, int IdChatLieu, int IdDoDay, int IdNhaCungCap, String TenSPCT, Date NgayTao, Date NgaySua, BigDecimal GiaBan, BigDecimal GiaNhap, String Anh, String TrangThai) {
        this.ID = ID;
        this.MaSPCT = MaSPCT;
        this.SoLuong = SoLuong;
        this.IdSanPham = IdSanPham;
        this.IdMauSac = IdMauSac;
        this.IdSize = IdSize;
        this.IdChatLieu = IdChatLieu;
        this.IdDoDay = IdDoDay;
        this.IdNhaCungCap = IdNhaCungCap;
        this.TenSPCT = TenSPCT;
        this.NgayTao = NgayTao;
        this.NgaySua = NgaySua;
        this.GiaBan = GiaBan;
        this.GiaNhap = GiaNhap;
        this.Anh = Anh;
        this.TrangThai = TrangThai;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getMaSPCT() {
        return MaSPCT;
    }

    public void setMaSPCT(String MaSPCT) {
        this.MaSPCT = MaSPCT;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int SoLuong) {
        this.SoLuong = SoLuong;
    }

    public int getIdSanPham() {
        return IdSanPham;
    }

    public void setIdSanPham(int IdSanPham) {
        this.IdSanPham = IdSanPham;
    }

    public int getIdMauSac() {
        return IdMauSac;
    }

    public void setIdMauSac(int IdMauSac) {
        this.IdMauSac = IdMauSac;
    }

    public int getIdSize() {
        return IdSize;
    }

    public void setIdSize(int IdSize) {
        this.IdSize = IdSize;
    }

    public int getIdChatLieu() {
        return IdChatLieu;
    }

    public void setIdChatLieu(int IdChatLieu) {
        this.IdChatLieu = IdChatLieu;
    }

    public int getIdDoDay() {
        return IdDoDay;
    }

    public void setIdDoDay(int IdDoDay) {
        this.IdDoDay = IdDoDay;
    }

    public int getIdNhaCungCap() {
        return IdNhaCungCap;
    }

    public void setIdNhaCungCap(int IdNhaCungCap) {
        this.IdNhaCungCap = IdNhaCungCap;
    }

    public String getTenSPCT() {
        return TenSPCT;
    }

    public void setTenSPCT(String TenSPCT) {
        this.TenSPCT = TenSPCT;
    }

    

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

    public Date getNgaySua() {
        return NgaySua;
    }

    public void setNgaySua(Date NgaySua) {
        this.NgaySua = NgaySua;
    }

    public BigDecimal getGiaBan() {
        return GiaBan;
    }

    public void setGiaBan(BigDecimal GiaBan) {
        this.GiaBan = GiaBan;
    }

    public BigDecimal getGiaNhap() {
        return GiaNhap;
    }

    public void setGiaNhap(BigDecimal GiaNhap) {
        this.GiaNhap = GiaNhap;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String Anh) {
        this.Anh = Anh;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    
    
}
