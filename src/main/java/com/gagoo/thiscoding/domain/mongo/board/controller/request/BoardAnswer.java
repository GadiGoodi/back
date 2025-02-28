package com.gagoo.thiscoding.domain.mongo.board.controller.request;

import jakarta.validation.constraints.NotBlank;

public record BoardAnswer(@NotBlank String content) {
}
