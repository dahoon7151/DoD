package com.dahoon.toy.artcollector.game.document;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "Game_Details")
@Builder
public class GameDetail {
    @Id
    private String id;

    private String platform;
    private boolean success;

    private Map<String, Object> data;
}
