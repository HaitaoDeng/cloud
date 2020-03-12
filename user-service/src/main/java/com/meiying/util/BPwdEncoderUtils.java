package com.meiying.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BPwdEncoderUtils {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String BCryptPassword(String password) {
        return encoder.encode(password);
    }

    public static boolean matches(CharSequence rawPassword, String encoderPassword) {
        return encoder.matches(rawPassword, encoderPassword);
    }
}
