package com.daviddai.retail.config;

import java.nio.charset.StandardCharsets;

import org.springframework.security.crypto.bcrypt.BCrypt;

import com.daviddai.retail.exception.ValidationException;

public final class BcryptPasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(String password) {
        if (password == null || password.getBytes(StandardCharsets.UTF_8).length > 72) {
            throw new ValidationException("Mật khẩu không được vượt quá 72 byte UTF-8");
        }
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    @Override
    public boolean matches(String password, String encodedPassword) {
        if (password == null || encodedPassword == null
                || !encodedPassword.matches("\\$2[aby]\\$\\d{2}\\$[./A-Za-z0-9]{53}")
                || password.getBytes(StandardCharsets.UTF_8).length > 72)
            return false;
        try {
            return BCrypt.checkpw(password, encodedPassword);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
