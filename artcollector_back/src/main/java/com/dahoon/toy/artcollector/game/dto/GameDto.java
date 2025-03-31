package com.dahoon.toy.artcollector.game.dto;

import com.dahoon.toy.artcollector.game.document.Game;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GameDto {
    private String appId;
    private String name;
//    private String url;
//    private String img;
//    private String release;
//    private String rate;
//    private String price;
//    private String discount;

    public GameDto(Game game) {
        this.appId = game.getAppId();
        this.name = game.getName();
//        this.url = game.getUrl();
//        this.img = game.getImg();
//        this.release = game.getRelease();
//        this.rate = game.getRate();
//        this.price = game.getPrice();
//        this.discount = game.getDiscount();
    }
}
