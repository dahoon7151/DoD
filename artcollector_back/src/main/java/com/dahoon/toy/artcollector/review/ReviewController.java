package com.dahoon.toy.artcollector.review;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/reviews")
@Tag(name = "Review API")
public class ReviewController {
    private final ReviewService reviewService;

    public ResponseEntity<Page<ReviewDto>> getReviewsByGame(
            @Parameter(description = "최하 별점 필터링", in = ParameterIn.QUERY)
            @RequestParam(required = false) Integer minRating) {

        return null;
    }
}
