package com.pravi.backend.praviproject.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Component
public class JWTUtil {

    private static final String SECRET = "SEGREDO_SUPER_SEGURO_AQUI_32_CHARS_MINIMO";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    private static final long EXPIRATION = 1000 * 60 * 60 * 24; // 24h

    public String gerarToken(Long id, String email) {
        return Jwts.builder()
                .subject(String.valueOf(id))
                .claim("email", email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SECRET_KEY, Jwts.SIG.HS256)
                .compact();
    }

    public Claims validarToken(String token) {
        return Jwts.parser()              // <-- funciona em 0.12.x
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getIdUsuario(String token) {
        return Long.parseLong(validarToken(token).getSubject());
    }
}
