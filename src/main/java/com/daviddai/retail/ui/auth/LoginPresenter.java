package com.daviddai.retail.ui.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daviddai.retail.app.AppContext;
import com.daviddai.retail.exception.AsyncException;
import com.daviddai.retail.exception.AuthException;
import com.daviddai.retail.exception.GlobalExceptionHandler;
import com.daviddai.retail.exception.ValidationException;
import com.daviddai.retail.model.dto.command.LoginCommand;
import com.daviddai.retail.model.dto.result.EmployeeResult;
import com.daviddai.retail.service.AuthService;
import com.daviddai.retail.ui.component.MainFrame;

public class LoginPresenter {

    private static final Logger log = LoggerFactory.getLogger(LoginPresenter.class);

    private final LoginView view;
    private final AuthService authService;

    public LoginPresenter(LoginView view, AuthService authService) {
        this.view = view;
        this.authService = authService;
        view.addLoginListener(e -> login());
        view.addCloseListener(e -> onClose());
    }

    private void login() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isBlank() && password.isBlank()) {
            view.showError("Vui lòng nhập tài khoản và mật khẩu");
            return;
        }
        if (username.isBlank()) {
            view.showError("Không được để trống tài khoản");
            return;
        }
        if (password.isBlank()) {
            view.showError("Không được để trống mật khẩu");
            return;
        }

        view.clearError();
        view.showLoading(true);

        AsyncException.run(
                () -> {
                    LoginCommand command = new LoginCommand(username, password);
                    return new EmployeeResult(
                            1, // id
                            "EMP001", // code
                            "Nguyễn Văn A", // fullName
                            "0901234567", // phone
                            LocalDate.of(1995, 5, 20), // birthDate
                            "ADMIN", // role
                            true, // gender (true = Nam, false = Nữ)
                            "123 Lê Lợi, Q.1, TP.HCM", // address
                            "nva95", // username
                            LocalDateTime.now(), // createdAt
                            true // active
                    ); // return authService.login(req);
                },
                employeeResult -> {
                    log.info("Đăng nhập thành công: {}", employeeResult.username());
                    new MainFrame(employeeResult).setVisible(true);
                    view.close();
                    ;
                },
                e -> {
                    if (e instanceof AuthException || e instanceof ValidationException) {
                        view.showError(e.getMessage());
                    } else {
                        GlobalExceptionHandler.handle(e);
                    }
                },
                () -> view.showLoading(false));
    }

    private void onClose() {
        if (view.confirmClose()) {
            AppContext.getInstance().close();
            view.close();
            System.exit(0);
        }
    }
}
