package com.gagoo.thiscoding.domain.maria.user.infrastructure.security;

import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@PropertySource("classpath:config/application-jwt.properties")
public class JWTUtil {

    private static final String JWT_HS256_ALGORITHM = Jwts.SIG.HS256.key().build().getAlgorithm();
    private final Long accessTokenExpTime;
    private final Long refreshTokenExpTime;
    private final SecretKey secretKey;


    public JWTUtil(@Value("${spring.jwt.key}") String secret,
                    @Value("${spring.jwt.atk}") Long atk,
                    @Value("${spring.jwt.rtk}") Long rtk
    ) {
        this.secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        JWT_HS256_ALGORITHM
                );
        this.accessTokenExpTime = atk;
        this.refreshTokenExpTime = rtk;
    }

    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("email", String.class);
    }

    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("role", String.class);
    }

    public Boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    public String createJwt(String email, String role) {
        return Jwts.builder()
                .claim("email", email)
                .claim("role", role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpTime)) // 소멸시간
                .signWith(secretKey)
                .compact();
    }
}
