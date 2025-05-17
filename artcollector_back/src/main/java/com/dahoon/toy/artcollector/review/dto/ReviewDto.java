package com.dahoon.toy.artcollector.review.dto;

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
    private String writer;
    private String lastModifiedTime;
    private int likeCount;

    public ReviewDto(String content, int rating, String gameId) {
        this.content = content;
        this.rating = rating;
        this.gameId = gameId;
    }

    public static ReviewDto toDto(Review review, String writer) {
        String modifiedTime = (review.getUpdatedTime() != null)
                ? review.getUpdatedTime().toString()
                : review.getCreatedTime().toString();

        return new ReviewDto(
                review.getContent(),
                review.getRating(),
                review.getGameId(),
                writer,
                modifiedTime,
                review.getLikeCount());
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
