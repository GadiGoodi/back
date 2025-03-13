package com.gagoo.thiscoding.domain.mongo.board.controller.port;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface VisitorIdProvider {
    String getVisitorId(HttpServletRequest request, HttpServletResponse response);
}
