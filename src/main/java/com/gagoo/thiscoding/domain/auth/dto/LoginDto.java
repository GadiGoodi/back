package com.gagoo.thiscoding.domain.auth.dto;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.auth.domain.Token;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginDto {
    private final User user;
    private final Token token;

    public static LoginDto of(User user, Token token) {
        return new LoginDto(user, token);
    }
}
