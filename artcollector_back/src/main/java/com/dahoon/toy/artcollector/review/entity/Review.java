package com.dahoon.toy.artcollector.review.entity;

import com.dahoon.toy.artcollector.member.entity.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private String title;
    private String content;

    @Column(nullable = false)
    private Integer rating;

    // GameDetail 참조

    // Member 참조

    // ReviewLike 참조
}
