package com.daviddai.retail.app;

import com.daviddai.retail.exception.GlobalExceptionHandler;
import com.daviddai.retail.ui.UiUtils;
import com.daviddai.retail.ui.auth.LoginPresenter;
import com.daviddai.retail.ui.auth.LoginScreen;
import com.daviddai.retail.ui.auth.LoginView;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class RetailApplication {

    public static void main(String[] args) {
        System.out.println(UiUtils.gennerateCodeFromName(""));
        try {
            SwingUtilities.invokeLater(() -> {
                FlatLightLaf.setup();
                GlobalExceptionHandler.install();
                LoginView view = new LoginScreen();
                new LoginPresenter(view, AppContext.getInstance().getAuthService());
                ((LoginScreen) view).setVisible(true);
            });
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Không thể khởi động ứng dụng:\n" + e.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
}
