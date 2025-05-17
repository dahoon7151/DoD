package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.dto.ReviewDto;
import com.dahoon.toy.artcollector.review.dto.ReviewLikeDto;
import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.review.entity.ReviewLike;
import com.dahoon.toy.artcollector.review.repository.ReviewLikeRepository;
import com.dahoon.toy.artcollector.review.repository.ReviewRepository;
import com.dahoon.toy.artcollector.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
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
    private final ReviewRepository reviewRepository;
    private final ReviewLikeRepository reviewLikeRepository;
    private final EntityManager entityManager;

    @Transactional
    public ReviewDto writeReview(ReviewDto request, User user) {
        Review review = reviewRepository.save(request.toEntity(user));
        log.info("리뷰 저장 완료");

        return ReviewDto.toDto(review, user.getNickname());
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

        return reviewPage.map(review -> {
            String writer = review.getUser().isDeleted()
                    ? "탈퇴한 사용자"
                    : review.getUser().getNickname();
            return ReviewDto.toDto(review, writer);
        });
    }

    @Transactional(readOnly = true)
    public ReviewDto getReview(Long id) {
                Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 ID의 리뷰가 없습니다."));
        log.info("리뷰 단일 조회 완료");

        String writer = (review.getUser().isDeleted())
                ? "탈퇴한 사용자"
                : review.getUser().getNickname();

        return ReviewDto.toDto(review, writer);
    }

    @Transactional
    public void deleteReview(Long id, User user) {
        reviewRepository.deleteByIdAndCheckUser(id, user);
        log.info("리뷰 삭제 완료");
    }

    @Transactional
    public ReviewDto updateReview(Long id, User user, ReviewDto reviewDto) {
        reviewRepository.updateByIdAndCheckUser(id, user, reviewDto);
        log.info("리뷰 수정 완료");
        entityManager.flush();
        entityManager.clear();
        Review review = reviewRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("해당 ID의 리뷰가 없습니다."));
        log.info("수정된 리뷰 확인");

        return ReviewDto.toDto(review, user.getNickname());
    }

    @Transactional
    public ReviewLikeDto toggleReviewLike(User user, Long reviewId) {
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("해당 ID의 리뷰가 없습니다."));
        ReviewLike like = ReviewLike.builder()
                .review(review)
                .user(user)
                .build();
        try {
            reviewLikeRepository.save(like);
            log.info("좋아요 등록");
            return ReviewLikeDto.toDto(true, reviewRepository.likeCountUp(reviewId));
        } catch (DataIntegrityViolationException e) {
            reviewLikeRepository.deleteByUserAndReview(user, review);
            log.info("좋아요 취소");
            return ReviewLikeDto.toDto(false, reviewRepository.likeCountDown(reviewId));
        }
    }
}
