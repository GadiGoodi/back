package com.gagoo.thiscoding.domain.auth.service.port;

public interface SecurityUtils {
    String getUserEmail();
    String getUserNickname();
    boolean isLogin();
}
