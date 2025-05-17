package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.review.entity.ReviewLike;
import com.dahoon.toy.artcollector.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
    void deleteByUserAndReview(User user, Review review);
}
