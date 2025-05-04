package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ReviewRepositoryImplTest {

    @Autowired
    private EntityManager em;

    @Autowired
    private ReviewRepositoryImpl reviewRepositoryImpl;

    @BeforeEach
    void setUp() {
        User user1 = User.builder().email("dahoon1@mail.com").password("다훈").build();
        User user2 = User.builder().email("dahoon2@mail.com").password("다훈다훈").build();
        em.persist(user1);
        em.persist(user2);
        em.persist(Review.builder().content("꿀잼").rating(8).gameId("steam_1").user(user1).likeCount(0).build());
        em.persist(Review.builder().content("갓겜").rating(9).gameId("steam_1").user(user2).likeCount(0).build());
        em.persist(Review.builder().content("똥겜").rating(2).gameId("steam_1").user(user2).likeCount(0).build());
    }

    @Test
    void findAllByGameIdAndRatingFilter_성공() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("rating").descending());
        Page<Review> result = reviewRepositoryImpl.findAllByGameIdAndRatingFilter("steam_1", 4, pageable);

        assertEquals(2, result.getContent().size());
        assertEquals(9, result.getContent().get(0).getRating());
        assertEquals(8, result.getContent().get(1).getRating());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void deleteByIdAndCheckUser_성공() {

    }
}