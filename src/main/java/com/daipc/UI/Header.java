package com.daipc.UI;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Header extends javax.swing.JPanel {

    private final Color color1;
    private final Color color2;
    private final MainFrame frame;
    private boolean isMaximized = false;

    public Header(MainFrame frame, String hoTen) {
        initComponents();
        setOpaque(false);
        color1 = Color.decode("#36D1DC");
        color2 = Color.decode("#5B86E5");
        this.frame = frame;
        init();
        
        if(hoTen != null) {
            txt_name.setText(hoTen);
        }
    }
    

    @SuppressWarnings("unchecked")

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        lblClose = new javax.swing.JLabel();
        lblMinimize = new javax.swing.JLabel();
        txt_name = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblMaximize = new javax.swing.JLabel();

        panel.setBackground(new java.awt.Color(204, 204, 204));
        panel.setOpaque(false);

        lblClose.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClose.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/close1.png"))); // NOI18N
        lblClose.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        lblClose.setIconTextGap(0);

        lblMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMinimize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/minimize1.png"))); // NOI18N

        txt_name.setText("Admin");
        txt_name.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nameMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                txt_nameMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                txt_nameMouseExited(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/user.png"))); // NOI18N
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jLabel3MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jLabel3MouseExited(evt);
            }
        });

        lblMaximize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblMaximize.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/maximize1.png"))); // NOI18N

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                .addGap(0, 378, Short.MAX_VALUE)
                .addComponent(txt_name)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblMaximize, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblClose, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblMinimize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(lblClose, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(txt_name, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblMaximize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        frame.showForm(11);
    }//GEN-LAST:event_jLabel3MouseClicked

    private void txt_nameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nameMouseClicked
        frame.showForm(11);
    }//GEN-LAST:event_txt_nameMouseClicked

    private void jLabel3MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseEntered
        jLabel3.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_jLabel3MouseEntered

    private void jLabel3MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseExited
        jLabel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_jLabel3MouseExited

    private void txt_nameMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nameMouseEntered
        txt_name.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }//GEN-LAST:event_txt_nameMouseEntered

    private void txt_nameMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nameMouseExited
        txt_name.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_txt_nameMouseExited

    public void init() {
        
        lblClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblClose.setOpaque(true);
                lblClose.setBackground(new Color(255, 42, 28));
                lblClose.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/close2.png")));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblClose.setOpaque(false);
                lblClose.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/close1.png")));
                repaint();
            }
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        lblMinimize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblMinimize.setOpaque(true);
                lblMinimize.setBackground(new Color(0, 0, 0, 80));
                lblMinimize.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/minimize2.png")));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblMinimize.setOpaque(false);
                lblMinimize.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/minimize1.png")));
                repaint();
            }
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.setState(JFrame.ICONIFIED);
            }
        });
        
        lblMaximize.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                lblMaximize.setOpaque(true);
                lblMaximize.setBackground(new Color(0, 0, 0, 80));
                lblMaximize.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/maximize2.png")));
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                lblMaximize.setOpaque(false);
                lblMaximize.setIcon(new ImageIcon(getClass().getResource("/com/daipc/icon/maximize1.png")));
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (isMaximized) {
                    frame.setExtendedState(JFrame.NORMAL);
                } else {
                    frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                    revalidate();
                    repaint();
                }
                isMaximized = !isMaximized;
            }
        });
    }

    private Point initialClick;

    public void initMoving(JFrame frame) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // Lấy vị trí hiện tại của con trỏ chuột trên màn hình
                initialClick = e.getLocationOnScreen();
            }
        });

        panel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                // Lấy vị trí hiện tại của con trỏ chuột trên màn hình
                Point currentPoint = e.getLocationOnScreen();

                // Tính toán chênh lệch giữa vị trí hiện tại và vị trí ban đầu của con trỏ chuột
                int xOffset = currentPoint.x - initialClick.x;
                int yOffset = currentPoint.y - initialClick.y;

                // Đặt vị trí mới cho frame
                int frameX = frame.getLocation().x + xOffset;
                int frameY = frame.getLocation().y + yOffset;
                frame.setLocation(frameX, frameY);

                // Cập nhật lại vị trí ban đầu để tính toán tiếp theo
                initialClick = currentPoint;
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 0, 0);
        super.paintComponent(grphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel lblClose;
    private javax.swing.JLabel lblMaximize;
    private javax.swing.JLabel lblMinimize;
    private javax.swing.JPanel panel;
    private javax.swing.JLabel txt_name;
    // End of variables declaration//GEN-END:variables
}
