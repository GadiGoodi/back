package com.gagoo.thiscoding.domain.auth.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;
import com.gagoo.thiscoding.domain.auth.domain.Token;

public interface TokenFactory {
    Token createToken(User user);

    void remove(String email);
}
