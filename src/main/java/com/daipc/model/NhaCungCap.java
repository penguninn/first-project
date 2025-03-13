/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author acer
 */
public class NhaCungCap {
    private Integer id;
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String diaChi;
    private String lienHe;

    public NhaCungCap() {
    }

    public NhaCungCap(Integer id, String maNhaCungCap, String tenNhaCungCap, String diaChi, String lienHe) {
        this.id = id;
        this.maNhaCungCap = maNhaCungCap;
        this.tenNhaCungCap = tenNhaCungCap;
        this.diaChi = diaChi;
        this.lienHe = lienHe;
    }
    
    public Object[] getNhaCungCap(){
        return new Object[]{id, maNhaCungCap, tenNhaCungCap, diaChi, lienHe};
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLienHe() {
        return lienHe;
    }

    public void setLienHe(String lienHe) {
        this.lienHe = lienHe;
    }
    
    
}
