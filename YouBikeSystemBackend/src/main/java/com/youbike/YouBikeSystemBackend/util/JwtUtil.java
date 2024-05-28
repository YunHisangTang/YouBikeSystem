package com.youbike.YouBikeSystemBackend.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private final Key jwtSigningKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public String generateToken(String phoneNumber, String idCardNumber, String email, String easyCardNumber) {
        return Jwts.builder()
                .setSubject(phoneNumber)
                .claim("idCardNumber", idCardNumber)
                .claim("email", email)
                .claim("easyCardNumber", easyCardNumber)
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000))
                .signWith(jwtSigningKey)
                .compact();
    }

    public Claims extractClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(jwtSigningKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractPhoneNumber(String token) {
        return extractClaims(token).getSubject();
    }
}
