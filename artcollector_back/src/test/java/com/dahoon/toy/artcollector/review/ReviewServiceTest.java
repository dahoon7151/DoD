package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.dto.ReviewDto;
import com.dahoon.toy.artcollector.review.dto.ReviewLikeDto;
import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.review.entity.ReviewLike;
import com.dahoon.toy.artcollector.review.repository.ReviewLikeRepository;
import com.dahoon.toy.artcollector.review.repository.ReviewRepository;
import com.dahoon.toy.artcollector.user.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;
    @Mock
    private ReviewRepository reviewRepository;
    @Mock
    private ReviewLikeRepository reviewLikeRepository;
    @Mock
    private EntityManager entityManager;

    private User user1;
    private User user2;
    private List<Review> reviewList;

    @BeforeEach
    void setUp() {
        user1 = User.builder().email("dahoon1@mail.com").password("다훈").nickname("tester1").build();
        user2 = User.builder().email("dahoon2@mail.com").password("다훈다훈").nickname("tester2").build();

        reviewList = List.of(
                Review.builder().id(1L).content("갓겜").rating(9).gameId("steam_1").user(user2).build(),
                Review.builder().id(2L).content("꿀잼").rating(8).gameId("steam_1").user(user1).build()
        );
        reviewList.get(0).setCreatedTime(LocalDateTime.now());
        reviewList.get(1).setCreatedTime(LocalDateTime.now());
    }

    @Test
    void 리뷰등록(){
        //given
        ReviewDto request = new ReviewDto("재밌어요", 10, "steam_1234");
        Review review = request.toEntity(user1);
        review.setCreatedTime(LocalDateTime.now());
        given(reviewRepository.save(any(Review.class))).willReturn(review);
        //when
        ReviewDto response = reviewService.writeReview(request, user1);
        //then
        assertEquals("재밌어요", response.getContent());
        assertEquals(10, response.getRating());
        assertEquals("steam_1234", response.getGameId());
        assertEquals("tester1", response.getWriter());
    }

    @Test
    void 게임별리뷰목록조회(){
        //given
        String gameId = "steam_1";
        int page = 0;
        int count = 5;
        String order = "rating";
        Pageable pageable = PageRequest.of(page, count, Sort.by(Sort.Order.desc("rating")));
        Page<Review> reviewPage = new PageImpl<>(reviewList, pageable, 10);
        given(reviewRepository.findAllByGameId(gameId, pageable)).willReturn(reviewPage);
        //when
        Page<ReviewDto> reviewDtos = reviewService.getReviewsByGame(page, count, order, gameId, null);
        //then
        assertEquals("꿀잼", reviewDtos.getContent().get(1).getContent());
        assertEquals("갓겜", reviewDtos.getContent().get(0).getContent());
        assertEquals("tester2", reviewDtos.getContent().get(0).getWriter());
    }

    @Test
    void 리뷰단일조회(){
        //given
        Long id = reviewList.get(0).getId();
        given(reviewRepository.findById(id)).willReturn(Optional.ofNullable(reviewList.get(0)));
        //when
        ReviewDto reviewDto = reviewService.getReview(id);
        //then
        assertEquals("갓겜", reviewDto.getContent());
        assertEquals("tester2", reviewDto.getWriter());
    }

    @Test
    void 리뷰삭제(){
        //given
        Long id = reviewList.get(0).getId();
        //when
        reviewService.deleteReview(id, user1);
        //then
        Mockito.verify(reviewRepository).deleteByIdAndCheckUser(id, user1);
    }

    @Test
    void 리뷰수정(){
        //given
        Long id = reviewList.get(0).getId();
        User user = reviewList.get(0).getUser();
        ReviewDto request = new ReviewDto("초갓겜", 10, "steam_1");
        Review review = request.toEntity(user);
        review.setCreatedTime(LocalDateTime.now());
        given(reviewRepository.findById(id)).willReturn(Optional.ofNullable(review));
        //when
        ReviewDto result = reviewService.updateReview(id, user, request);
        //then
        assertEquals("초갓겜", result.getContent());
        assertEquals(10, result.getRating());
        assertEquals("tester2", result.getWriter());
    }

    @Test
    void 리뷰좋아요(){
        //given
        User user = user1;
        Long reviewId = reviewList.get(0).getId();
        given(reviewRepository.findById(reviewId)).willReturn(Optional.ofNullable(reviewList.get(0)));
        given(reviewRepository.likeCountUp(reviewId)).willReturn(reviewList.get(0).getLikeCount()+1);
        //when
        ReviewLikeDto result = reviewService.toggleReviewLike(user, reviewId);
        //then
        assertTrue(result.isLiked());
        assertEquals(1, result.getLikeCount());
    }

    @Test
    void 리뷰좋아요_취소(){
        //given
        User user = user1;
        Long reviewId = reviewList.get(0).getId();
        given(reviewRepository.findById(reviewId)).willReturn(Optional.ofNullable(reviewList.get(0)));
        given(reviewLikeRepository.save(any())).willThrow(DataIntegrityViolationException.class);
        given(reviewRepository.likeCountDown(reviewId)).willReturn(reviewList.get(0).getLikeCount()-1);
        //when
        ReviewLikeDto result = reviewService.toggleReviewLike(user, reviewId);
        //then
        Mockito.verify(reviewLikeRepository).deleteByUserAndReview(user1, reviewList.get(0));
        assertFalse(result.isLiked());
        assertEquals(-1, result.getLikeCount());
    }
}
