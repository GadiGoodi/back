package com.gagoo.thiscoding.domain.maria.user.infrastructure.security;

import com.gagoo.thiscoding.domain.maria.user.service.port.JwtUtil;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtilImpl implements JwtUtil {

    private static final String JWT_HS256_ALGORITHM = Jwts.SIG.HS256.key().build().getAlgorithm();
    private static final String EMAIL = "email";
    private static final String ROLE = "role";

    private final SecretKey secretKey;

    @Value("${spring.jwt.atk}")
    private Long accessTokenExpTime;

    @Value("${spring.jwt.rtk}")
    private Long refreshTokenExpTime;

    public JwtUtilImpl(@Value("${spring.jwt.key}") String secret) {
        this.secretKey =
                new SecretKeySpec(
                        secret.getBytes(StandardCharsets.UTF_8),
                        JWT_HS256_ALGORITHM
                );
    }

    @Override
    public String getUsername(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(EMAIL, String.class);
    }

    @Override
    public String getRole(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get(ROLE, String.class);
    }

    /**
     * 토큰이 만료되었는지 확인
     * 토큰의 만료 시간이 현재 시간 이전이면 true(만료)
     * 토큰의 만료 시간이 현재 시간 이후면 false(유효)
     */
    @Override
    public boolean isExpired(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()
                .before(new Date());
    }

    /**
     * 엑세스 토큰 발급
     */
    @Override
    public String createAtk(String email, String role) {
        return Jwts.builder()
                .claim(EMAIL, email)
                .claim(ROLE, role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpTime.longValue()))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 리프레쉬 토큰 발급
     */
    public String createRtk(String email, String role) {
        return Jwts.builder()
                .claim(EMAIL, email)
                .claim(ROLE, role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpTime.longValue()))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 리프레쉬 토큰 발급 후 쿠키에 저장하여 반환
     */
    public Cookie createRefreshTokenCookie(String value) {
        Cookie cookie = new Cookie("rtk", "Bearer" + value);
        cookie.setMaxAge(refreshTokenExpTime.intValue());
        cookie.setHttpOnly(true);

        return cookie;
    }

    /**
     * 토큰 유효성 검사
     */
    public void validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseClaimsJws(token);

        } catch (SecurityException | MalformedJwtException e) {
            throw new GlobalException(ErrorCode.INVALID_SIGNATURE);
        } catch (ExpiredJwtException e) {
            throw new GlobalException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            throw new GlobalException(ErrorCode.INVALID_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new GlobalException(ErrorCode.CLAIM_NOT_FOUND);
        }
    }
}
