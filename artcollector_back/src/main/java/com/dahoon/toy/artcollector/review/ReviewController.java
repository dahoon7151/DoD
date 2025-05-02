package com.dahoon.toy.artcollector.review;

import com.dahoon.toy.artcollector.user.User;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reviews")
@Tag(name = "Review API")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<ReviewDto> writeReview(@RequestBody ReviewDto request, @AuthenticationPrincipal User user) {
        log.info("컨트롤러 - 리뷰 작성");
        ReviewDto reviewDto = reviewService.writeReview(request, user);
        return ResponseEntity.status(HttpStatus.OK).body(reviewDto);
    }
    public ResponseEntity<Page<ReviewDto>> getReviewsByGame(
            @Parameter(description = "최하 별점 필터링", in = ParameterIn.QUERY)
            @RequestParam(required = false) Integer minRating) {

        return null;
    }
}
