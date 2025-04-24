package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
