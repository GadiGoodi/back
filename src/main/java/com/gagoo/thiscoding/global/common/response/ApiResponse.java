package com.gagoo.thiscoding.global.common.response;

import com.gagoo.thiscoding.global.exception.GlobalException;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;

public record ApiResponse<T>(
        @JsonIgnore HttpStatus httpStatus,
        boolean success,
        @Nullable String message,
        @Nullable T data
) {

    public static <T> ApiResponse<T> ok(final T data, String message) {
        return new ApiResponse<>(HttpStatus.OK, true, message, data);
    }

    public static <T> ApiResponse<T> created(final T data, String message) {
        return new ApiResponse<>(HttpStatus.CREATED, true, message, data);
    }

    public static <T> ApiResponse<T> error(final HttpStatus status, String message, T data) {
        return new ApiResponse<>(status, false, message,data);
    }

    public static <T> ApiResponse<T> error(final HttpStatus status, String message) {
        return new ApiResponse<>(status, false, message,null);
    }

}