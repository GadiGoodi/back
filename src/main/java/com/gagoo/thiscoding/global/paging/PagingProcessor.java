package com.gagoo.thiscoding.global.paging;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import com.gagoo.thiscoding.global.paging.dto.PageRequest;
import com.gagoo.thiscoding.global.paging.exception.PageIndexException;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@NoArgsConstructor
public class PagingProcessor {

    public static Pageable toPageable(PageRequest pageRequest) {
        return org.springframework.data.domain.PageRequest.of(
            pageRequest.getPage() - 1, // 1-based → 0-based 변환
            pageRequest.getSize(),
            Sort.by(Sort.Direction.fromString(pageRequest.getDirection()), pageRequest.getSort())
        );
    }

    public static void validate(PageRequest pageRequest) {
        if (pageRequest.getPage() < 1) {
            throw new PageIndexException(ErrorCode.INVALID_PAGE_INDEX);
        }
        if (pageRequest.getSize() < 1) {
            throw new PageIndexException(ErrorCode.INVALID_PAGE_SIZE);
        }
    }

    public static Pageable getPageable(int page, int size) {
        PageRequest pageRequest = PageRequest.builder()
            .page(page)
            .size(size)
            .sort("id")
            .direction("DESC")
            .build();
        PagingProcessor.validate(pageRequest);
        return PagingProcessor.toPageable(pageRequest);
    }
}
