package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.ReviewLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewLikeRepository extends JpaRepository<ReviewLike, Long> {
}
