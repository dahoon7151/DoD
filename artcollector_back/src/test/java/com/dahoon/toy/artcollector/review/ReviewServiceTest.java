package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import com.dahoon.toy.artcollector.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @InjectMocks ReviewService reviewService;

    private User user1;

    @BeforeEach
    void setUp() {
        user1 = User.builder()
                .username("dahoon1234")
                .nickname("다훈")
                .build();
    }

    @Test
    void 리뷰작성_성공(){
        //given
        ReviewDto request = new ReviewDto("재밌어요", 10, "steam_1234");
        //when
        ReviewDto response = reviewService.writeReview(request, user1);

        //then
        assertEquals("재밌어요", response.getContent());
        assertEquals(10, response.getRating());
        assertEquals("steam_1234", response.getGameId());
    }
}
