/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author acer
 */
public class DoDay {
    private Integer id;
    private String maDoDay;
    private String tenDoDay;

    public DoDay() {
    }

    public DoDay(Integer id, String maDoDay, String tenDoDay) {
        this.id = id;
        this.maDoDay = maDoDay;
        this.tenDoDay = tenDoDay;
    }
    
    public Object[] getDoDay(){
        return new Object[]{id,maDoDay,tenDoDay};
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaDoDay() {
        return maDoDay;
    }

    public void setMaDoDay(String maDoDay) {
        this.maDoDay = maDoDay;
    }

    public String getTenDoDay() {
        return tenDoDay;
    }

    public void setTenDoDay(String tenDoDay) {
        this.tenDoDay = tenDoDay;
    }
    
}
