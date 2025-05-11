package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDto {
    private String content;
    private Integer rating;
    private String gameId;
    private String writer;
    private String lastModifiedTime;

    public ReviewDto(String content, int rating, String gameId) {
        this.content = content;
        this.rating = rating;
        this.gameId = gameId;
    }

    public static ReviewDto toDto(Review review) {
        String modifiedTime = (review.getUpdatedTime() != null)
                ? review.getUpdatedTime().toString()
                : review.getCreatedTime().toString();

        String writer = (review.getUser().isDeleted())
                ? "탈퇴한 사용자"
                : review.getUser().getNickname();

        return new ReviewDto(
                review.getContent(),
                review.getRating(),
                review.getGameId(),
                writer,
                modifiedTime);
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
