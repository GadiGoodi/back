package com.gagoo.thiscoding.global.security.aop;

import com.gagoo.thiscoding.domain.maria.user.domain.contants.Role;
import org.springframework.http.HttpStatus;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

@Retention(RUNTIME)
@Target(METHOD)
public @interface AuthorizationRequired {
    Role[] value();
    HttpStatus status() default HttpStatus.UNAUTHORIZED;
}