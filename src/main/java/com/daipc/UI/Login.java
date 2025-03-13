/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.daipc.UI;

import com.daipc.model.NhanVien;
import com.daipc.repo.QuanLiTaiKhoan;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLightLaf;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

/**
 *
 * @author vitvu
 */
public class Login extends javax.swing.JFrame {
    private final QuanLiTaiKhoan qltk = new QuanLiTaiKhoan();
    public NhanVien nv;

    /**
     * Creates new form Login
     */
    public Login() {
        this.setUndecorated(true);
        initComponents();
        this.setLocationRelativeTo(null);
        this.init();
    }

    public void init() {

        jPanel1.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,3%);");
        jPanel2.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,3%);");
        jPanel3.putClientProperty(FlatClientProperties.STYLE, " " + "arc:12;");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, " "
                + "showRevealButton:true");
        btn_login.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]background:darken(@background,10%);"
                + "margin:4,6,4,6;"
                + "borderWidth:0;"
                + "focusWidth:0;"
                + "innerFocusWidth:0");

        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");

        jLabel2.putClientProperty(FlatClientProperties.STYLE, " "
                + "font:bold +10");
        description.putClientProperty(FlatClientProperties.STYLE, " "
                + "[light]foreground:lighten(@foreground,30%);");

        final boolean[] checkBorder = new boolean[2];
        checkBorder[0] = checkBorder[1] = true;
        Border defaultBorder = txtUsername.getBorder();
        Border marginBorder = BorderFactory.createEmptyBorder(3, 3, 3, 3);
        Border compoundBorderBlue = BorderFactory.createCompoundBorder(new RoundedBorder(8, new Color(89, 183, 249), 2), BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(57, 132, 241), 2), marginBorder));
        Border compoundBorderRed = BorderFactory.createCompoundBorder(new RoundedBorder(8, new Color(255, 87, 51), 2), BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(new Color(255, 51, 0), 2), marginBorder));

        String originalStyle = (String) lbl_close.getClientProperty(FlatClientProperties.STYLE);

        String hoverStyle = originalStyle + ";"
                + "border: 1,1,1,1, #FF5733,, 5;";

        lbl_close.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                jPanel3.setBackground(new Color(89, 183, 249));
                jPanel3.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                jPanel3.setBackground(Color.WHITE);
                jPanel3.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        btn_login.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn_login.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn_login.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });

        txtUsername.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (checkBorder[1]) {
                    if (checkBorder[0]) {
                        txtUsername.setBorder(compoundBorderBlue);
                    } else {
                        txtUsername.setBorder(compoundBorderRed);
                    }
                } else {
                    txtUsername.setBorder(compoundBorderRed);
                    if (!txtUsername.getText().isEmpty()) {
                        txtUsername.setBorder(compoundBorderBlue);
                        description.setText(" ");
                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUsername.setBorder(defaultBorder);

            }
        });

        txtPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (checkBorder[1]) {
                    if (checkBorder[0]) {
                        txtPassword.setBorder(compoundBorderBlue);
                    } else {
                        txtPassword.setBorder(compoundBorderRed);
                    }
                } else {
                    txtPassword.setBorder(compoundBorderRed);
                    if (!new String(txtPassword.getPassword()).trim().isEmpty()) {
                        txtPassword.setBorder(compoundBorderBlue);
                        description.setText(" ");
                    }
                }

            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPassword.setBorder(defaultBorder);
            }
        });

        txtUsername.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    return;
                }
                if (!checkBorder[1]) {
                    txtUsername.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
                if (!checkBorder[0]) {
                    txtUsername.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        txtPassword.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == '\n') {
                    return;
                }
                if (!checkBorder[1]) {
                    txtPassword.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
                if (!checkBorder[0]) {
                    txtPassword.setBorder(compoundBorderBlue);
                    description.setText(" ");
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });

        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btn_login.doClick();
                }
            }
        });

        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    btn_login.doClick();
                }
            }
        });
    }
    
    public boolean loginHandle() {
        ArrayList<NhanVien> listNV = qltk.getAccount();
        for(NhanVien nv : listNV) {
            if(txtUsername.getText().trim().equals(nv.getTaiKhoan())) {
                if(txtPassword.getText().trim().equals(nv.getMatKhau())) {
                   this.nv = nv;
                   return true;
                }
            }
        }
        return false;
    }
    
    public boolean isEmty() {
        if (txtUsername.getText().trim().isEmpty()) {
            description.setText("Không được để trống tên tài khoản !");
            description.setForeground(Color.red);
            txtUsername.requestFocus();
            return false;
        } else if (new String(txtPassword.getPassword()).trim().isEmpty()) {
            description.setText("Không được để trống mật khẩu !");
            description.setForeground(Color.red);
            txtPassword.requestFocus();
            return false;
        }

        return true;
    }

    static class RoundedBorder implements Border {

        private final int radius;
        private final Color color;
        private final int thickness;

        RoundedBorder(int radius, Color color, int thickness) {
            this.radius = radius;
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        description = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        jPanel3 = new javax.swing.JPanel();
        lbl_close = new javax.swing.JLabel();
        btn_login = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(244, 244, 244));

        jPanel2.setBackground(new java.awt.Color(244, 244, 244));

        jLabel2.setFont(new java.awt.Font("Helvetica Neue", 1, 23)); // NOI18N
        jLabel2.setText("Chào Mừng Quay Lại !");

        description.setFont(new java.awt.Font("Helvetica", 0, 13)); // NOI18N
        description.setForeground(new java.awt.Color(153, 153, 153));
        description.setText("Đăng nhập để sử dụng phần mềm");

        txtUsername.setText("nva");

        jLabel3.setFont(new java.awt.Font("Helvetica", 0, 13)); // NOI18N
        jLabel3.setText("Tài khoản");

        jLabel4.setFont(new java.awt.Font("Helvetica", 0, 13)); // NOI18N
        jLabel4.setText("Mật khẩu");

        txtPassword.setText("123");

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lbl_close.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/daipc/icon/close.png"))); // NOI18N
        lbl_close.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_closeMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(lbl_close)
                .addGap(3, 3, 3))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(3, 3, 3)
                .addComponent(lbl_close)
                .addGap(3, 3, 3))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(description, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 183, Short.MAX_VALUE))
            .addComponent(txtPassword)
            .addComponent(txtUsername)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2))
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(description)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtUsername, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btn_login.setBackground(new java.awt.Color(221, 221, 221));
        btn_login.setFont(new java.awt.Font("Helvetica Neue", 1, 15)); // NOI18N
        btn_login.setForeground(new java.awt.Color(51, 51, 51));
        btn_login.setText("Login");
        btn_login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_loginActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btn_login, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btn_login)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lbl_closeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_closeMouseClicked
        System.exit(0);
    }//GEN-LAST:event_lbl_closeMouseClicked

    private void btn_loginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_loginActionPerformed
        if(isEmty()) {
            if(loginHandle()) {
                MainFrame mf = new MainFrame(nv);
                mf.setVisible(true);
                this.dispose();
            } else {
                    description.setText("Sai tên đăng nhập hoặc mật khẩu !!!");
                    description.setForeground(Color.red);
                    txtUsername.requestFocus();
                }
        }
    }//GEN-LAST:event_btn_loginActionPerformed

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_login;
    private javax.swing.JLabel description;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lbl_close;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}
