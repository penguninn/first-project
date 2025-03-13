/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author DaiPc
 */
public class ThongKeDT {
    private String date;
    private BigDecimal doanhThu;
    private BigDecimal loiNhuan;

    public ThongKeDT(String date, BigDecimal doanhThu, BigDecimal loiNhuan) {
        this.date = date;
        this.doanhThu = doanhThu;
        this.loiNhuan = loiNhuan;
    }

    public ThongKeDT() {
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(BigDecimal doanhThu) {
        this.doanhThu = doanhThu;
    }

    public BigDecimal getLoiNhuan() {
        return loiNhuan;
    }

    public void setLoiNhuan(BigDecimal loiNhuan) {
        this.loiNhuan = loiNhuan;
    }
    
    
}
