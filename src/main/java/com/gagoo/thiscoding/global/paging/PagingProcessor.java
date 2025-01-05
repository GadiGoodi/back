package com.gagoo.thiscoding.global.paging;

import com.gagoo.thiscoding.global.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import com.gagoo.thiscoding.global.paging.exception.PageIndexException;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PagingProcessor {

    public static Pageable toPageable(int page, int size) {
        validate(page, size);
        return PageRequest.of(
            page - 1, // 1-based → 0-based
            size,
            Sort.by(Direction.DESC, "id")
        );
    }

    public static void validate(int page, int size) {
        if (page < 1) {
            throw new PageIndexException(ErrorCode.INVALID_PAGE_INDEX);
        }
        if (size < 1) {
            throw new PageIndexException(ErrorCode.INVALID_PAGE_SIZE);
        }
    }
}
