package com.hcmus.auction.common.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "DangDuyKhang09092001";
    private static final Long ACCESS_TOKEN_EXPIRE_AFTER_MS = 600000L;

    public static String createAccessToken(String payload) {
        Date current = new Date();
        return Jwts.builder()
                .setSubject(payload)
                .setIssuedAt(current)
                .setExpiration(new Date(current.getTime() + ACCESS_TOKEN_EXPIRE_AFTER_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }
}
