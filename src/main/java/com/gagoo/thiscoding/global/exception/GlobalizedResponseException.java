package com.gagoo.thiscoding.global.exception;

import com.gagoo.thiscoding.global.common.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalizedResponseException {

    @ExceptionHandler(GlobalException.class)
    public final ApiResponse<?> handleGlobalException(final GlobalException e, WebRequest request) {

        return ApiResponse.error(
                e.getErrorCode().getStatus(),
                e.getErrorCode().getMessage()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<List<ExceptionResponse>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request){

        List<ExceptionResponse> errors = new ArrayList<>();

        e.getBindingResult().getAllErrors().forEach(c -> {
            errors.add(new ExceptionResponse(c.getDefaultMessage(),
                    request.getDescription(false)));
        });

        return ApiResponse.error(HttpStatus.BAD_REQUEST, "유효성 검사에 실패하였습니다", errors);

    }

}