package com.daipc.swing;

import com.daipc.modelUI.Model_Menu;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MenuItem extends javax.swing.JPanel {
    
    private boolean selected;
    private boolean isDisable;

    public boolean isIsDisable() {
        return isDisable;
    }

    public void setIsDisable(boolean isDisable) {
        this.isDisable = isDisable;
    }
    
    public MenuItem(Model_Menu data, boolean isDisable) {
        initComponents();
        setOpaque(false);
        this.isDisable = isDisable;
        if(data.getType() == Model_Menu.MenuType.MENU){
            if(!isDisable) {
                lblName.setForeground(new Color(225, 225, 225, 150));
                repaint();
            }
            lblIcon.setIcon(data.toIcon());
            lblName.setText(data.getName());
        } else if(data.getType() == Model_Menu.MenuType.TITLE){
            lblIcon.setText(data.getName());
            lblIcon.setFont(new Font("sansserif", 1, 14));
            lblName.setVisible(false);
        } else {
            this.setPreferredSize(new Dimension(0, 10));
            lblName.setText("");
        }
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblIcon = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();

        lblIcon.setForeground(new java.awt.Color(255, 255, 255));

        lblName.setFont(new java.awt.Font("SansSerif", 1, 12)); // NOI18N
        lblName.setForeground(new java.awt.Color(255, 255, 255));
        lblName.setText("MenuName");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(lblIcon)
                .addGap(18, 18, 18)
                .addComponent(lblName)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        if(selected) {
            Graphics2D g2 = (Graphics2D) grphcs;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(new Color(255, 255, 255, 80));
            g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 5, 5);
        }
        if(!isDisable) {
//            Graphics2D g2 = (Graphics2D) grphcs;
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            g2.setColor(new Color(0, 0, 0, 50));
//            g2.fillRoundRect(10, 0, getWidth() - 20, getHeight(), 0, 0);
        }
        super.paintComponent(grphcs);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lblIcon;
    private javax.swing.JLabel lblName;
    // End of variables declaration//GEN-END:variables
}
