package com.dahoon.toy.artcollector.review.repository;

import com.dahoon.toy.artcollector.common.config.QueryDSLConfig;
import com.dahoon.toy.artcollector.review.ReviewDto;
import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.user.User;
import com.dahoon.toy.artcollector.user.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({QueryDSLConfig.class, ReviewRepositoryImpl.class})
@ActiveProfiles("test")
class ReviewRepositoryImplTest {

    @Autowired
    private ReviewRepositoryImpl reviewRepositoryImpl;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private Review review1;
    private Review review2;
    private Review review3;

    @BeforeEach
    void setUp() {
        user1 = userRepository.save(User.builder().email("dahoon1@test.com").password("다훈").build());
        user2 = userRepository.save(User.builder().email("dahoon2@test.com").password("다훈다훈").build());

        review1 = reviewRepository.save(Review.builder().content("test꿀잼").rating(8).gameId("steam_1").user(user1).likeCount(0).build());
        review2 = reviewRepository.save(Review.builder().content("test갓겜").rating(9).gameId("steam_1").user(user2).likeCount(0).build());
        review3 = reviewRepository.save(Review.builder().content("test똥겜").rating(2).gameId("steam_1").user(user2).likeCount(0).build());
    }

    @Test
    void findAllByGameIdAndRatingFilter_성공() {
        //given
        Pageable pageable = PageRequest.of(0, 2, Sort.by("rating").descending());
        //when
        Page<Review> result = reviewRepositoryImpl.findAllByGameIdAndRatingFilter("steam_1", 4, pageable);
        //then
        assertEquals(2, result.getContent().size());
        assertEquals(9, result.getContent().get(0).getRating());
        assertEquals(8, result.getContent().get(1).getRating());
        assertEquals(2, result.getTotalElements());
    }

    @Test
    void deleteByIdAndCheckUser_성공() {
        //given
        Long id = review1.getId();
        User user = review1.getUser();
        //when
        reviewRepositoryImpl.deleteByIdAndCheckUser(id, user);
        //then
        assertFalse(reviewRepository.existsById(id));
    }

    @Test
    void deleteByIdAndCheckUser_사용자불일치실패(){
        //given
        Long id = review1.getId();
        //when & then
        assertThrows(EntityNotFoundException.class, () -> reviewRepositoryImpl.deleteByIdAndCheckUser(id, user2));
    }

    @Test
    void saveByIdAndCheckUser_성공() {
        //given
        Long id = review1.getId();
        User user = review1.getUser();
        ReviewDto reviewDto = new ReviewDto("초갓겜", 10, "steam_1");
        //when
        reviewRepositoryImpl.saveByIdAndCheckUser(id, user, reviewDto);
        entityManager.flush();
        entityManager.clear();

        //then
        Review result = reviewRepository.findById(id).orElseThrow(() -> new AssertionError("리뷰 수정 확인 실패"));
        assertEquals("초갓겜", result.getContent());
        assertEquals(10, result.getRating());
    }
}