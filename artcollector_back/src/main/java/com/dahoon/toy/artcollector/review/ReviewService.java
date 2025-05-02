package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.review.repository.ReviewRepository;
import com.dahoon.toy.artcollector.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    public Page<ReviewDto> getReviewsByGame(int page, int count, String order, String gameId, Integer minRating) {
        Sort.Order sort;
        if (order.equals("rating")) {
            sort = Sort.Order.desc("rating");
        } else if (order.equals("like")) {
            sort = Sort.Order.desc("likeCount");
        } else {
            throw new IllegalArgumentException("잘못된 정렬 기준입니다.");
        }
        Pageable pageable = PageRequest.of(page, count, Sort.by(sort));

        Page<Review> reviewPage;
        if (minRating != null) {
            reviewPage = reviewRepository.findAllByGameIdAndRatingFilter(gameId, minRating, pageable);
        } else {
            reviewPage = reviewRepository.findAllByGameId(gameId, pageable);
        }
        log.info("리뷰 목록 조회 완료");

        return reviewPage.map(ReviewDto::toDto);
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long id) {
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 ID의 리뷰가 없습니다."));
        log.info("리뷰 단일 조회 완료");

        return ReviewDto.toDto(review);
    }
}
