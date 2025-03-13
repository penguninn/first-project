/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.daipc.form;

import com.daipc.repo.QuanLiTaiKhoan;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

/**
 *
 * @author vitvu
 */
public class Popup_DoiMK extends javax.swing.JFrame {

    private String MaNV = null;
    private String mkCu = null;

    public Popup_DoiMK(String MaNV, String mkCu) {
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(null);
        this.setTitle("Đổi mật khẩu");

        this.MaNV = MaNV;
        this.mkCu = mkCu;

        pwCu.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        pwMoi.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");
        pwMoi2.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");

        btn_doimk.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,10%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        btn_huy.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,10%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");
    }

    public boolean isEmpty() {
        if (pwCu.getText().isEmpty() && pwMoi.getText().isEmpty() && pwMoi2.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống các trường!");
            return false;
        } else {
            if (pwCu.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu cũ!");
                pwCu.requestFocus();

                return false;
            } else if (pwMoi.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập mật khẩu mới!");
                pwMoi.requestFocus();

                return false;
            } else if (pwMoi2.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Bạn chưa nhập lại mật khẩu mới!");
                pwMoi2.requestFocus();

                return false;
            }
        }

        return true;
    }

    public boolean handle() {
        if (new QuanLiTaiKhoan().doiMK(this.MaNV, pwMoi2.getText()) != 0) {
            JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelBorder1 = new com.daipc.swing.PanelBorder();
        pwCu = new javax.swing.JPasswordField();
        jLabel2 = new javax.swing.JLabel();
        pwMoi = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        pwMoi2 = new javax.swing.JPasswordField();
        btn_doimk = new javax.swing.JButton();
        btn_huy = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelBorder1.setBackground(new java.awt.Color(255, 255, 255));

        pwCu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                pwCuFocusGained(evt);
            }
        });

        jLabel2.setText("Mật khẩu cũ");

        jLabel3.setText("Mật khẩu mới");

        jLabel4.setText("Nhập lại mật khẩu");

        btn_doimk.setText("Đổi mật khẩu");
        btn_doimk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_doimkActionPerformed(evt);
            }
        });

        btn_huy.setText("Huỷ");
        btn_huy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_huyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBorder1Layout = new javax.swing.GroupLayout(panelBorder1);
        panelBorder1.setLayout(panelBorder1Layout);
        panelBorder1Layout.setHorizontalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pwCu, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelBorder1Layout.createSequentialGroup()
                            .addComponent(btn_doimk, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(btn_huy, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel4)
                        .addComponent(pwMoi2, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addComponent(pwMoi)
                        .addComponent(jLabel2)))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        panelBorder1Layout.setVerticalGroup(
            panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBorder1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwCu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwMoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pwMoi2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelBorder1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_doimk)
                    .addComponent(btn_huy))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelBorder1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_huyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_huyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btn_huyActionPerformed

    private void pwCuFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_pwCuFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_pwCuFocusGained

    private void btn_doimkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_doimkActionPerformed
        if (isEmpty()) {
            if (pwCu.getText().equals(mkCu)) {
                if (pwMoi.getText().equals(pwMoi2.getText())) {
                    handle();
                } else {
                    JOptionPane.showMessageDialog(this, "Nhập lại không khớp mật khẩu!!");
                    pwMoi.requestFocus();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sai mật khẩu cũ!");
                pwCu.requestFocus();
            }
        }
    }//GEN-LAST:event_btn_doimkActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Popup_DoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Popup_DoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Popup_DoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Popup_DoiMK.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Popup_DoiMK(null, null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_doimk;
    private javax.swing.JButton btn_huy;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.daipc.swing.PanelBorder panelBorder1;
    private javax.swing.JPasswordField pwCu;
    private javax.swing.JPasswordField pwMoi;
    private javax.swing.JPasswordField pwMoi2;
    // End of variables declaration//GEN-END:variables
}
