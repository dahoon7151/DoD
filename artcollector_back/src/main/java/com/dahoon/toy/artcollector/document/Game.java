package com.dahoon.toy.artcollector.document;

import jakarta.persistence.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "Crawler_Steam")
public class Game {
    @Id
    private String _id;
    private String steamId;
    private String title;
    private String url;
    private String img;
    private String release;
    private String rate;
    private String price;
    private String discount;

}
