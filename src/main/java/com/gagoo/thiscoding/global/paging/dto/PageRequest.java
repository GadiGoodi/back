package com.gagoo.thiscoding.global.paging.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PageRequest {
    @Builder.Default
    private int page = 1;
    @Builder.Default
    private int size = 10;
    @Builder.Default
    private String sort = "id";
    @Builder.Default
    private String direction = "DESC";
}