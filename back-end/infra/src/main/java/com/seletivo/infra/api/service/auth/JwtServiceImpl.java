package com.seletivo.infra.api.service.auth;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtServiceImpl implements JwtService {

    @Value("${security.jwt.expiration-minutes}")
    private Long jwtExpirationInMs;

    @Value("${security.jwt.refresh-expiration-hours}")
    private Long refreshTokenExpirationInMs;

    @Value("${security.jwt.secret}")
    private String secret;


    private SecretKey key;
    private SecretKey keyRefresh;
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.keyRefresh = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

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
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs * 60 * 1000))
                .signWith(key)
                .compact();
    }

    @Override
    public String generateRefreshToken(Authentication authentication) {
        User userPrincipal = (User) authentication.getPrincipal();
        return Jwts.builder()
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date())
                .expiration( new Date(System.currentTimeMillis() + refreshTokenExpirationInMs * 60 * 60 * 1000))
                .signWith(keyRefresh)
                .compact();
    }

    @Override
    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + jwtExpirationInMs * 60 * 1000))
                .signWith(key)
                .compact();
    }

    @Override
    public String generateRefreshTokenFromUsername(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration( new Date(System.currentTimeMillis() + refreshTokenExpirationInMs * 60 * 60 * 1000))
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
