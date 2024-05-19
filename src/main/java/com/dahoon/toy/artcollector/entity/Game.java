package com.dahoon.toy.artcollector.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private String developers;

    @Column
    private String publisher;

    @Column
    private String releaseDate;

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Member> memberList = new ArrayList<>();
}
