package com.pravi.backend.praviproject.Utils;

import java.util.Base64;

public class PasswordEncoder {
    public static String encode(String rawPassword) {
        return Base64.getEncoder().encodeToString(rawPassword.getBytes());
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        String encodedRaw = encode(rawPassword);
        return encodedRaw.equals(encodedPassword);
    }
}
