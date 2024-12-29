package com.gagoo.thiscoding.domain.maria.user.infrastructure.security;

import com.gagoo.thiscoding.domain.maria.user.service.port.JwtUtil;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.exception.GlobalException;
import com.gagoo.thiscoding.global.security.JwtProperties;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.gagoo.thiscoding.global.security.constants.SecurityConstants.*;

@Component
public class JwtUtilImpl implements JwtUtil {

    private static final String JWT_HS256_ALGORITHM = Jwts.SIG.HS256.key().build().getAlgorithm();
    private final SecretKey secretKey;
    private final JwtProperties jwtProperties;

    public JwtUtilImpl(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey =
                new SecretKeySpec(
                        jwtProperties.getSecretKey().getBytes(StandardCharsets.UTF_8),
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

    @Override
    public Long getExpirationTime(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getExpiration()  // JWT의 표준 'exp' claim을 가져옵니다
                .getTime() / 1000;  // 밀리초를 초 단위로 변환
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
     * 공통 토큰 생성 메서드
     */
    private String createToken(String email, String role, Long expirationTime) {
        return Jwts.builder()
                .claim(EMAIL, email)
                .claim(ROLE, role)
                .issuedAt(new Date(System.currentTimeMillis())) // 발행시간
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(secretKey)
                .compact();
    }

    /**
     * 엑세스 토큰 발급
     */
    @Override
    public String createAtk(String email, String role, Long expTime) {
        return createToken(email, role, expTime);
    }

    /**
     * 리프레쉬 토큰 발급
     */
    public String createRtk(String email, String role, Long expTime) {
        return createToken(email, role, expTime);
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
