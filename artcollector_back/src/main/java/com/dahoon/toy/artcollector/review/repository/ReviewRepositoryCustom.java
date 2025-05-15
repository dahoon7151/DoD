package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.ReviewDto;
import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> findAllByGameIdAndRatingFilter(String gameId, Integer minRating, Pageable pageable);

    void deleteByIdAndCheckUser(Long id, User user);

    void updateByIdAndCheckUser(Long id, User user, ReviewDto reviewDto);
}
