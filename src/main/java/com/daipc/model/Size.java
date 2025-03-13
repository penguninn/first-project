/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author acer
 */
public class Size {
    private Integer id;
    private String maSize;
    private String tenSize;

    public Size() {
    }

    public Size(Integer id, String maSize, String tenSize) {
        this.id = id;
        this.maSize = maSize;
        this.tenSize = tenSize;
    }
    
    public Object[] getSize(){
        return new Object[]{id,maSize,tenSize};
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaSize() {
        return maSize;
    }

    public void setMaSize(String maSize) {
        this.maSize = maSize;
    }

    public String getTenSize() {
        return tenSize;
    }

    public void setTenSize(String tenSize) {
        this.tenSize = tenSize;
    }
    
}
