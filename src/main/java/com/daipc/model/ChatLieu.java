/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.daipc.model;

/**
 *
 * @author acer
 */
public class ChatLieu {
    private Integer id;
    private String maChatLieu;
    private String tenChatLieu;

    public ChatLieu() {
    }

    public ChatLieu(Integer id, String maChatLieu, String tenChatLieu) {
        this.id = id;
        this.maChatLieu = maChatLieu;
        this.tenChatLieu = tenChatLieu;
    }
    
    public Object[] getChatLieu(){
        return new Object[]{id,maChatLieu,tenChatLieu};
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaChatLieu() {
        return maChatLieu;
    }

    public void setMaChatLieu(String maChatLieu) {
        this.maChatLieu = maChatLieu;
    }

    public String getTenChatLieu() {
        return tenChatLieu;
    }

    public void setTenChatLieu(String tenChatLieu) {
        this.tenChatLieu = tenChatLieu;
    }
    
    
}
