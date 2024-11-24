package com.gagoo.thiscoding.domain.maria.user.infrastructure;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "joinCode", timeToLive = 60 * 5)
public class JoinCodeEntity {

    @Id
    private String joinCode;

}
