package com.users.api.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class PasswordUtil {
    public static String encrypt(String password) {
        return Hashing.sha256()
                .hashString(password, StandardCharsets.UTF_8)
                .toString();
    }
}