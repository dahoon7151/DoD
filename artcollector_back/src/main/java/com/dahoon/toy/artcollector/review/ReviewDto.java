package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewDto {
    private String content;
    private Integer rating;
    private String gameId;

    public static ReviewDto toDto(Review review) {
        return new ReviewDto(
                review.getContent(),
                review.getRating(),
                review.getGameId());
    }

    public Review toEntity(User user) {
        return Review.builder()
                .content(content)
                .rating(rating)
                .gameId(gameId)
                .user(user)
                .likeCount(0)
                .build();
    }
}
