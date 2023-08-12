package com.hcmus.auction.common.util;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class BcryptUtil {
    private static final int SALT_ROUNDS = 10;

    public static String hashText(String text) {
        return BCrypt.hashpw(text, BCrypt.gensalt(SALT_ROUNDS));
    }

    public static boolean isSameText(String plainText, String hashedText) {
        return BCrypt.checkpw(plainText, hashedText);
    }
}
