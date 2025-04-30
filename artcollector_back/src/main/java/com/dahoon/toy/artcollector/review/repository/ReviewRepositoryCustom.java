package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReviewRepositoryCustom {
    Page<Review> findAllByGameIdAndRatingFilter(String gameId, Integer minRating, Pageable pageable);
}
