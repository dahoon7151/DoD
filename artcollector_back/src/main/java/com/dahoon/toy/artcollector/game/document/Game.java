package com.dahoon.toy.artcollector.game.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(indexName = "game")
public class Game {  // 게임 목록 조회 성능 개선용 요약 문서
    @Id
    private String id;

    private Integer appid;
    private String name;

    public Game(Integer appid, String name) {
        this.appid = appid;
        this.name = name;
    }
}
