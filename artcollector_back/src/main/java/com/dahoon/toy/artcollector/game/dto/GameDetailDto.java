package com.dahoon.toy.artcollector.game.dto;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@AllArgsConstructor
public class GameDetailDto {
    private String id;

    private String platform;
    private boolean success;

    private Map<String, Object> data;

    public static GameDetailDto toDto(GameDetail gameDetail) {
        return new GameDetailDto(
                gameDetail.getId(),
                gameDetail.getPlatform(),
                gameDetail.getSuccess(),
                gameDetail.getData()
        );
    }
}
