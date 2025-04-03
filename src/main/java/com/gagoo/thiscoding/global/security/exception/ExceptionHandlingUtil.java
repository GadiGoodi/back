package com.gagoo.thiscoding.global.security.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gagoo.thiscoding.global.exception.ErrorCode;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ExceptionHandlingUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) throws IOException {
        response.setStatus(errorCode.getStatus().value());
        response.setContentType("application/json;charset=UTF-8");

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("message", errorCode.getMessage());
        responseBody.put("status", errorCode.getStatus().value());
        responseBody.put("error", errorCode.getStatus().getReasonPhrase());
        responseBody.put("code", errorCode.name());

        response.getWriter().write(objectMapper.writeValueAsString(responseBody));
    }
}
