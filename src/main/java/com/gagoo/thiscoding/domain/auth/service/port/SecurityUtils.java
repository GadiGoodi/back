package com.gagoo.thiscoding.domain.auth.service.port;

import com.gagoo.thiscoding.domain.maria.user.domain.User;

public interface SecurityUtils {

    User getUser();
    String getUserEmail();
}
