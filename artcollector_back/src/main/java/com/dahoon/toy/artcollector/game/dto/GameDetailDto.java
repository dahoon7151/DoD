package com.dahoon.toy.artcollector.game.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class GameDetailDto {
    private String id;

    private String platform;
    private boolean success;

    private Map<String, Object> data;
}
