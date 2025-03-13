package com.daipc.form;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class Popup_Bill extends JDialog {

    private static final String VIETQR_URL_TEMPLATE = "https://img.vietqr.io/image/%s-%s-%s.png?amount=%s&addInfo=%s&accountName=%s";

    public Popup_Bill(String bankId, String accountNo, String template, double amount, String description, String accountName) {
        setTitle("Hóa Đơn");
        setSize(400, 400);
        setLocationRelativeTo(null);
        setModal(true);

        String vietQRUrl = generateVietQRUrl(bankId, accountNo, template, amount, description, accountName);
        try {
            URL url = new URL(vietQRUrl);
            ImageIcon qrCodeImage = new ImageIcon(url);

            Image image = qrCodeImage.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            ImageIcon resizedImage = new ImageIcon(image);

            JLabel label = new JLabel(resizedImage);
            add(label, BorderLayout.CENTER);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }

    private String generateVietQRUrl(String bankId, String accountNo, String template,
            double amount, String description, String accountName) {
        try {
            String encodedDescription = URLEncoder.encode(description, StandardCharsets.UTF_8);
            String encodedAccountName = URLEncoder.encode(accountName, StandardCharsets.UTF_8);

            return String.format(VIETQR_URL_TEMPLATE,
                    bankId,
                    accountNo,
                    template,
                    String.format("%.0f", amount),
                    encodedDescription,
                    encodedAccountName);
        } catch (Exception e) {
            throw new RuntimeException("Error encoding URL parameters", e);
        }
    }

    public static void showQRCodeDialog(String bankId, String accountNo, String template,
            double amount, String description, String accountName) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Popup_Bill(bankId, accountNo, template, amount, description, accountName);
            }
        });
    }

    public static void main(String[] args) {
        // Các thông số cho mã QR
        String bankId = "970407";
        String accountNo = "19071676785014";
        String template = "compact";
        double amount = 15000; // Số tiền
        String description = "Thanh toán hóa đơn";
        String accountName = "Cửa Hàng Quần Abidas";

        // Hiển thị hộp thoại mã QR
        showQRCodeDialog(bankId, accountNo, template, amount, description, accountName);
    }
}
