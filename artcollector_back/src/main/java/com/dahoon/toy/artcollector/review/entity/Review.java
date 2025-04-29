package com.dahoon.toy.artcollector.review.entity;

import com.dahoon.toy.artcollector.common.BaseEntity;
import com.dahoon.toy.artcollector.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false)
    private String content;
    @Column(nullable = false)
    private Integer rating;
    @Column(nullable = false)
    private String gameId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<ReviewLike> likes = new ArrayList<>();

    @Column(nullable = false)
    private Integer likeCount;
}
