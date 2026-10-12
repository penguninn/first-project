package com.daviddai.retail.ui.auth;

import java.awt.event.ActionListener;

public interface LoginView {

    String getUsername();

    String getPassword();

    void showError(String message);

    void clearError();

    void showLoading(boolean loading);

    void close();

    boolean confirmClose();

    void addLoginListener(ActionListener l);

    void addCloseListener(ActionListener l);
}
