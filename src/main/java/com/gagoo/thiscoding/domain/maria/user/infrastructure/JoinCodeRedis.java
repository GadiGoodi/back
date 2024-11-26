package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import com.gagoo.thiscoding.domain.maria.user.domain.JoinCode;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@RedisHash(value = "joinCode", timeToLive = 60 * 5)
public class JoinCodeRedis {

    @Id
    private String email;

    @Indexed
    private String code;

    public static JoinCodeRedis from(JoinCode joinCode) {
        JoinCodeRedis joinCodeRedis = new JoinCodeRedis();
        joinCodeRedis.email = joinCode.getEmail();
        joinCodeRedis.code = joinCode.getCode();

        return joinCodeRedis;
    }

    public JoinCode toModel( ) {
        return JoinCode.builder()
                .email(email)
                .code(code)
                .build();
    }
}
