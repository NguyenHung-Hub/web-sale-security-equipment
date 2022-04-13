package com.metan.websalesecurityequipment.config;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncode {
    public static void main(String[] args) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = "123456";
        String passCode = passwordEncoder.encode(pass);
        System.out.println(passCode);
    }
}
