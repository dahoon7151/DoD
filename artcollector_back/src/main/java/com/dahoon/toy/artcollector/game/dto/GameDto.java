package com.dahoon.toy.artcollector.game.dto;

import com.dahoon.toy.artcollector.game.document.Game;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class GameDto {
    private String steamId;
    private String title;
    private String url;
    private String img;
    private String release;
    private String rate;
    private String price;
    private String discount;

    public GameDto(Game game) {
        this.steamId = game.getSteamId();
        this.title = game.getTitle();
        this.url = game.getUrl();
        this.img = game.getImg();
        this.release = game.getRelease();
        this.rate = game.getRate();
        this.price = game.getPrice();
        this.discount = game.getDiscount();
    }
}
