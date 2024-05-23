package com.dahoon.toy.artcollector.entity;

import com.dahoon.toy.artcollector.common.enumeration.GameGenre;
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

    @Column(nullable = false)
    private String developers;

    @Column(nullable = false)
    private String publisher;

    @Column(nullable = false)
    private String releaseDate;

    @Convert()
    private List<GameGenre> gameGenreList;

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Member> memberList = new ArrayList<>();
}
