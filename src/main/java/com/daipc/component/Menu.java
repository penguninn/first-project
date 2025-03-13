package com.daipc.component;

import com.daipc.event.EventMenuSelected;
import com.daipc.modelUI.Model_Menu;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;

public class Menu extends javax.swing.JPanel {

    private EventMenuSelected event;
    private int idDisable = -1;

    public void addEventMenuSelected(EventMenuSelected event) {
        this.event = event;
        listMenu1.addEventMenuSelected(event);
    }

    public Menu(int idDisable) {
        initComponents();
        setOpaque(false);
        listMenu1.setOpaque(false);
        this.idDisable = idDisable;
        listMenu1.setItemDisable(idDisable);
        init();
    }

    public void init() {
        listMenu1.addItem(new Model_Menu("1", "Thống Kê", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("2", "Sản Phẩm", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("3", "Bán Hàng", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("4", "Hóa Đơn", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("5", "Khuyến Mại", Model_Menu.MenuType.MENU));
        if(! (idDisable == 5)) {
            listMenu1.addItem(new Model_Menu("6", "Nhân Viên", Model_Menu.MenuType.MENU));
        } else {
            listMenu1.addItem(new Model_Menu("6_disable", "Nhân Viên", Model_Menu.MenuType.MENU));
        }
        
        listMenu1.addItem(new Model_Menu("7", "Khách Hàng", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("9", "Hoàn Trả", Model_Menu.MenuType.MENU));

        listMenu1.addItem(new Model_Menu("", " ", Model_Menu.MenuType.EMPTY));
        listMenu1.addItem(new Model_Menu("", "------------------------------", Model_Menu.MenuType.TITLE));
        listMenu1.addItem(new Model_Menu("", " ", Model_Menu.MenuType.EMPTY));

        listMenu1.addItem(new Model_Menu("8", "Tài Khoản", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("10", "Đăng xuất", Model_Menu.MenuType.MENU));
        listMenu1.addItem(new Model_Menu("", "", Model_Menu.MenuType.EMPTY));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelMoving = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        listMenu1 = new com.daipc.swing.ListMenu<>();

        panelMoving.setOpaque(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/shopping-cart.png"))); // NOI18N
        jLabel1.setText("Adidas");
        jLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 20, 1, 20));
        jLabel1.setIconTextGap(20);

        javax.swing.GroupLayout panelMovingLayout = new javax.swing.GroupLayout(panelMoving);
        panelMoving.setLayout(panelMovingLayout);
        panelMovingLayout.setHorizontalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelMovingLayout.setVerticalGroup(
            panelMovingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelMovingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelMoving, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(listMenu1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelMoving, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(listMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, 479, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#36D1DC"), 0, getHeight(), Color.decode("#5B86E5"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        super.paintChildren(grphcs);
    }

    private int x;
    private int y;

    public void initMoving(JFrame frame) {
        panelMoving.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });

        panelMoving.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                frame.setLocation(e.getXOnScreen() - x, e.getYOnScreen() - y);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private com.daipc.swing.ListMenu<String> listMenu1;
    private javax.swing.JPanel panelMoving;
    // End of variables declaration//GEN-END:variables
}
