package com.gagoo.thiscoding.domain.maria.bookmark.controller.port;

import com.gagoo.thiscoding.domain.maria.bookmark.domain.Bookmark;

public interface BookmarkService {
    Bookmark bookmarkQna(String qnaId);
    void cancelQnaBookmark(String qnaId);
}
