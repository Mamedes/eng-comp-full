package com.seletivo.infra.api.controller.login;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    private final long jwtExpirationInMs = 3000000L;
    private final long refreshTokenExpirationInMs = 86400000L;
    private static final String SECRET_STRING = "G1LyKRPljCT7YTlIiYzIEVjC9P1t413A4Fu2ZiKy4gd";

    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));
    private final SecretKey keyRefresh = Keys.hmacShaKeyFor(SECRET_STRING.getBytes(StandardCharsets.UTF_8));

    @PostConstruct
    public void post(){
        System.out.println(key.toString());
        System.out.println(key.getFormat());
    }
    @Override
    public String generateToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationInMs))
                .signWith(keyRefresh)
                .compact();
    }

    @Override
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(key)
                .compact();
    }

    @Override
    public String generateRefreshTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpirationInMs))
                .signWith(keyRefresh)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            var jws = Jwts.parser().verifyWith(key).build().parse(token);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException |
                 SignatureException e) {
            return false;
        }
    }
    @Override
    public boolean validateRefreshToken(String token) {
        try {
            var jws = Jwts.parser().verifyWith(keyRefresh).build().parse(token);
            return true;
        } catch (MalformedJwtException | UnsupportedJwtException | ExpiredJwtException | IllegalArgumentException | SignatureException e) {
            return false;
        }
    }

    @Override
    public String getUsernameFromToken(String token) {
        var jws = Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
        return jws.getPayload().getSubject();
    }

    @Override
    public String getUsernameFromRefreshToken(String token) {
        var jws = Jwts.parser().verifyWith(keyRefresh).build().parseSignedClaims(token);
        return jws.getPayload().getSubject();
    }

}
