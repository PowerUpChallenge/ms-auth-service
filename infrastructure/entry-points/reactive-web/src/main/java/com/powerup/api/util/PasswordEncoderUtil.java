package com.powerup.api.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String rawPassword = "ChangeMe123!";
        String encodedPassword = encoder.encode(rawPassword);
        System.out.println("Password encriptado: " + encodedPassword);
    }
}
