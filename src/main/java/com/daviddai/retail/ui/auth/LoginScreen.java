package com.daviddai.retail.ui.auth;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.formdev.flatlaf.FlatClientProperties;

import net.miginfocom.swing.MigLayout;

public class LoginScreen extends JFrame implements LoginView {

    private final JTextField txtUsername = new JTextField(20);
    private final JPasswordField txtPassword = new JPasswordField(20);
    private final JButton btnLogin = new JButton("Đăng nhập");
    private final JButton btnClose = new JButton("✕");
    private final JLabel lblTitle = new JLabel("Chào Mừng Quay Lại!");
    private final JLabel lblSubtitle = new JLabel("Đăng nhập để sử dụng phần mềm");
    private final JLabel lblError = new JLabel(" ");

    public LoginScreen() {
        initComponents();
        initLayout();
    }

    private void initComponents() {
        setTitle("Đăng nhập");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        // FlatLaf styling
        txtUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Tên đăng nhập");
        txtPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Mật khẩu");
        txtPassword.putClientProperty(FlatClientProperties.STYLE, "showRevealButton:true");

        btnLogin.putClientProperty(FlatClientProperties.STYLE,
                "background:#FFFFFF;" +
                        "margin:4,6,4,6;borderWidth:0;focusWidth:0;innerFocusWidth:0");

        btnClose.putClientProperty(FlatClientProperties.STYLE,
                "arc:8;borderWidth:0;background:#FFFFFF50;" +
                        "hoverBackground:#ff2a1c;foreground:#FFFFFF");

        lblTitle.putClientProperty(FlatClientProperties.STYLE, "font:bold +12; foreground:#FFFFFF");
        lblSubtitle.putClientProperty(FlatClientProperties.STYLE, "foreground:#FFFFFF");
        lblError.putClientProperty(FlatClientProperties.STYLE, "font:plain ; foreground:#f7000d");
    }

    private void initLayout() {
        JPanel root = new JPanel(new MigLayout(
                "fill, insets 30 60 30 60, gap 10",
                "[right, 100!][grow, 280!]",
                "[][][][][][][grow]")) {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                try {
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setPaint(new GradientPaint(
                            0, 0, Color.decode("#36D1DC"),
                            0, getHeight(), Color.decode("#509de2")));
                    g2.fillRect(0, 0, getWidth(), getHeight());
                } finally {
                    g2.dispose();
                }
            }
        };

        // Header: title + close button
        JPanel header = new JPanel(new MigLayout("fill, insets 0", "[grow][]", "[]"));
        header.setOpaque(false);
        header.add(lblTitle, "growx");
        header.add(btnClose);

        JLabel labelUsername = new JLabel("Tài khoản:");
        labelUsername.setForeground(Color.decode("#FFFFFF"));

        JLabel labelPassword = new JLabel("Mật khẩu:");
        labelPassword.setForeground(Color.decode("#FFFFFF"));

        root.add(header, "span 2, growx, wrap");
        root.add(lblSubtitle, "span 2, growx, gapbottom 16, wrap");

        root.add(labelUsername, "growx");
        root.add(txtUsername, "growx, h 32!, wrap");
        root.add(labelPassword, "growx");
        root.add(txtPassword, "growx, h 32!, wrap");

        root.add(lblError, "span 2, growx, h 20!, wrap");
        root.add(btnLogin, "span 2, growx, h 40!, gaptop 8");

        setContentPane(root);
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public String getUsername() {
        return txtUsername.getText().trim();
    }

    @Override
    public String getPassword() {
        return new String(txtPassword.getPassword()).trim();
    }

    @Override
    public void showError(String message) {
        lblError.setText(message);
    }

    @Override
    public void clearError() {
        lblError.setText(" ");
    }

    @Override
    public void showLoading(boolean loading) {
        btnLogin.setEnabled(!loading);
        btnLogin.setText(loading ? "Đang đăng nhập..." : "Đăng nhập");
        txtUsername.setEnabled(!loading);
        txtPassword.setEnabled(!loading);
        btnClose.setEnabled(!loading);
    }

    @Override
    public void close() {
        dispose();
    }

    @Override
    public void addLoginListener(ActionListener l) {
        btnLogin.addActionListener(l);
    }

    @Override
    public void addCloseListener(ActionListener l) {
        btnClose.addActionListener(l);
    }

    @Override
    public boolean confirmClose() {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Bạn có muốn thoát?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

}
