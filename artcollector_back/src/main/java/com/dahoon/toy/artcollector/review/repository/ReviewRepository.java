package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryCustom {

    @EntityGraph(attributePaths = "user")
    Page<Review> findAllByGameId(String gameId, Pageable pageable);

    @EntityGraph(attributePaths = "user")
    Optional<Review> findById(Long id);
}
