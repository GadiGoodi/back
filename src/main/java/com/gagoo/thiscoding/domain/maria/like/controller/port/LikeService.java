package com.gagoo.thiscoding.domain.maria.like.controller.port;

import com.gagoo.thiscoding.domain.maria.like.domain.Like;

public interface LikeService {
    Like likeAnswer(String qnaId);
    void cancelAnswerLike(String qnaId);
}
