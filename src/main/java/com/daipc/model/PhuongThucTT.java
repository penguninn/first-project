/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author daipc
 */
public class PhuongThucTT {
    private int id;
    private String tenPTTT;
    private int trangThai;

    public PhuongThucTT() {
    }

    public PhuongThucTT(int id, String tenPTTT, int trangThai) {
        this.id = id;
        this.tenPTTT = tenPTTT;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenPTTT() {
        return tenPTTT;
    }

    public void setTenPTTT(String tenPTTT) {
        this.tenPTTT = tenPTTT;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    
    
}
