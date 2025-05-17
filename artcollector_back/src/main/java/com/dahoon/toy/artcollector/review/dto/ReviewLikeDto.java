package com.dahoon.toy.artcollector.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewLikeDto {
    private boolean liked;
    private int likeCount;

    public static ReviewLikeDto toDto(boolean liked, int likeCount) {
        return new ReviewLikeDto(liked, likeCount);
    }
}
