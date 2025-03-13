package com.daipc.modelUI;

import java.awt.Color;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Model_Card {
    private String icon;
    private String title;
    private String price;
    private String color1;
    private String color2;

    public Model_Card() {
    }

    public Model_Card(String icon, String title, String price, String color1, String color2) {
        this.icon = icon;
        this.title = title;
        this.price = price;
        this.color1 = color1;
        this.color2 = color2;
    }

    public Icon toIcon() {
        try {
            return new ImageIcon(getClass().getResource("/com/daipc/icon/" + icon + ".png"));
        } catch (Exception e) {
            System.err.println("Icon not found: " + e.getMessage());
            return null; // or you can return a default icon
        }
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor1() {
        return color1;
    }

    public void setColor1(String color1) {
        this.color1 = color1;
    }

    public String getColor2() {
        return color2;
    }

    public void setColor2(String color2) {
        this.color2 = color2;
    }
    
    
}
