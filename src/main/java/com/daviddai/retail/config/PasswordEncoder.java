package com.daviddai.retail.config;

public interface PasswordEncoder {

    String encode(String password);

    boolean matches(String password, String encodedPassword);
}
