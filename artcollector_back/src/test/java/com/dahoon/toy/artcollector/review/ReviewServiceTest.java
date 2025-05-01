package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.review.entity.Review;
import com.dahoon.toy.artcollector.review.repository.ReviewRepository;
import com.dahoon.toy.artcollector.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    private User user1;
    private User user2;
    private List<Review> reviewList;

    @BeforeEach
    void setUp() {
        user1 = User.builder().email("dahoon1@mail.com").password("다훈").build();
        user2 = User.builder().email("dahoon2@mail.com").password("다훈다훈").build();

        reviewList = List.of(
                Review.builder().content("꿀잼").rating(8).gameId("steam_1").user(user1).likeCount(0).build(),
                Review.builder().content("갓겜").rating(9).gameId("steam_1").user(user2).likeCount(0).build()
        );
    }

    @Test
    void 리뷰등록(){
        //given
        ReviewDto request = new ReviewDto("재밌어요", 10, "steam_1234");
        Review review = request.toEntity(user1);
        given(reviewRepository.save(any(Review.class))).willReturn(review);

        //when
        ReviewDto response = reviewService.writeReview(request, user1);

        //then
        assertEquals("재밌어요", response.getContent());
        assertEquals(10, response.getRating());
        assertEquals("steam_1234", response.getGameId());
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

        //when
        Page<ReviewDto> reviewDtos = reviewService.getReviewsByGame(page, count, order, gameId, null);

        //then

    }
}
