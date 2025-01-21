package com.dahoon.toy.artcollector.game.document;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "Steam_Game")
public class SteamApp {
    @Id
    private String _id;

    private Long appid;
    private String name;
}
