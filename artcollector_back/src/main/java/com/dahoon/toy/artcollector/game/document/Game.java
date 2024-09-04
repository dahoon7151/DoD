package com.dahoon.toy.artcollector.game.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "Crawler_Steam")
public class Game {
    @Id
    private String _id;

    private String steamId;
    private String title;
    private String url;
    private String img;
    private String release;
    private String rate; // 0 ~ 100
    private String price;
    private String discount;

    public Game(String _id, String steamId, String title, String url, String img, String release, String rate, String price, String discount) {
        this._id = _id;
        this.steamId = steamId;
        this.title = title;
        this.url = url;
        this.img = img;
        this.release = release;
        this.rate = rate;
        this.price = price;
        this.discount = discount;
    }
}
