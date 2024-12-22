package com.gagoo.thiscoding.global.paging.dto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;

public class PageResponseFactory {

    public static <T> PageResponse<T> create(Page<T> page) {
        int currentPage = page.getNumber() + 1;
        int pageSize = page.getSize();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();
        List<T> content = page.getContent();

        List<Integer> pageNumList = IntStream.rangeClosed(
            Math.max(1, currentPage - 5),
            Math.min(totalPages, currentPage + 4)
        ).boxed().collect(Collectors.toList());

        return new PageResponse<>(
            currentPage,
            pageSize,
            totalPages,
            totalElements,
            hasNext,
            hasPrevious,
            pageNumList,
            content
        );
    }
}
