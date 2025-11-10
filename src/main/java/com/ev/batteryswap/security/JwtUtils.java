package com.ev.batteryswap.security;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;


@Component
public class JwtUtils {

    // khóa bí mật
    private static final String SECRET_KEY = "u$ax5^-#y0=mmd6p7^@4$$o=6klasg#q(@b1-5meaz-iy7pk^$";

    // thời gian sống của token JWT
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 giờ

    private SecretKey getSigningKey() {
        // Dùng StandardCharsets.UTF_8 để đảm bảo mã hóa ổn định
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    // Sinh token cho username và role_name
    public String generateToken(String username, String role_name) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .subject(username)
                .claim("role", role_name)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

}
