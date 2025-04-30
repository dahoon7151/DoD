package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {
    Page<Review> findAllByGameId(String gameId, Pageable pageable);
}
