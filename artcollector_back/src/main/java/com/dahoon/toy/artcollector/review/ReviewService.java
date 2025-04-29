package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {
    final ReviewRepository reviewRepository;

    @Transactional
    public ReviewDto writeReview(ReviewDto request, User user) {
        Review review = reviewRepository.save(request.toEntity(user));
        log.info("리뷰 저장 완료");

        return ReviewDto.toDto(review);
    }

    @Transactional(readOnly = true)
    public Page<ReviewDto> getReviewsByGame(int page, int count, String order, String gameId) {
        Page<Review> reviewPage = reviewRepository.findAllByGameId(gameId);
        if (order.equals("")) {

        }

        return ;
    }
}
