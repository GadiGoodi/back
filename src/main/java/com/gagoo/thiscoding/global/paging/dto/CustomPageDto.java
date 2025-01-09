package com.gagoo.thiscoding.global.paging.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class CustomPageDto<T> {
    private List<T> content;
    private Long totalElements;

    public CustomPageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
    }
}
