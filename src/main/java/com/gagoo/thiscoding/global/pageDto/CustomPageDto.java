package com.gagoo.thiscoding.global.pageDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class CustomPageDto<T> {
    private List<T> content;         // 현재 페이지의 데이터
    private List<Integer> pageNumList; // 페이지 번호 리스트
    private int currentPage;         // 현재 페이지
    private int pageSize;            // 페이지당 데이터 개수
    private int totalPages;          // 총 페이지 수
    private long totalElements;      // 총 데이터 수
    private boolean hasNext;         // 다음 페이지 존재 여부
    private boolean hasPrevious;     // 이전 페이지 존재 여부

    public CustomPageDto(int currentPage, int pageSize, int totalPages, long totalElements,
        boolean hasNext, boolean hasPrevious, List<Integer> pageNumList, List<T> content) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
        this.pageNumList = pageNumList;
        this.content = content;
    }

    public static <T> CustomPageDto<T> from(Page<T> page) {
        int currentPage = page.getNumber() + 1;
        int pageSize = page.getSize();
        int totalPages = page.getTotalPages();
        long totalElements = page.getTotalElements();
        boolean hasNext = page.hasNext();
        boolean hasPrevious = page.hasPrevious();

        List<Integer> pageNumList = IntStream.rangeClosed(
            Math.max(1, currentPage - 5),
            Math.min(totalPages, currentPage + 4)
        ).boxed().collect(Collectors.toList());

        return new CustomPageDto<>(
            currentPage,
            pageSize,
            totalPages,
            totalElements,
            hasNext,
            hasPrevious,
            pageNumList,
            page.getContent()
        );
    }
}
