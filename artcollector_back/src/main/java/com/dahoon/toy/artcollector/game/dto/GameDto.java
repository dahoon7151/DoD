package com.dahoon.toy.artcollector.game.dto;

import com.dahoon.toy.artcollector.game.document.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameDto {
    private Integer appId;
    private String name;
//    private String url;
//    private String img;
//    private String release;
//    private String rate;
//    private String price;
//    private String discount;

    public static GameDto toDto(Game game) {
        return new GameDto(
                game.getAppId(),
                game.getName());
    }
}
