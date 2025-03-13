/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author acer
 */
public class MauSac {
    private Integer id;
    private String maMauSac;
    private String tenMauSac;

    public MauSac() {
    }

    public MauSac(Integer id, String maMauSac, String tenMauSac) {
        this.id = id;
        this.maMauSac = maMauSac;
        this.tenMauSac = tenMauSac;
    }
    
    public Object[] getMauSac(){
        return new Object[]{id,maMauSac,tenMauSac};
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaMauSac() {
        return maMauSac;
    }

    public void setMaMauSac(String maMauSac) {
        this.maMauSac = maMauSac;
    }

    public String getTenMauSac() {
        return tenMauSac;
    }

    public void setTenMauSac(String tenMauSac) {
        this.tenMauSac = tenMauSac;
    }
    
}
