package com.gagoo.thiscoding.global.paging.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class CustomPageDto<T> {

    private final List<T> content;
    private final Long totalElements;
    private final int currentPage;
    private final int totalPage;
    private final int pageSize;

    private CustomPageDto(Page<T> page) {
        this.content = page.getContent();
        this.totalElements = page.getTotalElements();
        this.currentPage = page.getNumber();
        this.totalPage = page.getTotalPages();
        this.pageSize = page.getSize();
    }

    public static <T> CustomPageDto<T> of(Page<T> page) {
        return new CustomPageDto<>(page);
    }
}
