package com.dahoon.toy.artcollector.game.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "Game_Summary")
public class Game {  // 게임 목록 조회 성능 개선용 요약 문서
    @Id
    private String id;

    private String appId;
    private String name;

    public Game(String appId, String name) {
        this.appId = appId;
        this.name = name;
    }
}
