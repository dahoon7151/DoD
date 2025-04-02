package com.dahoon.toy.artcollector.game.message;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GameDetailSaveMessage {
    private String id;
    private String platform;
    private Boolean success;
    private Map<String, Object> data;

    public static GameDetailSaveMessage toDto(GameDetail gameDetail) {
        return new GameDetailSaveMessage(
                gameDetail.getId(),
                gameDetail.getPlatform(),
                gameDetail.getSuccess(),
                gameDetail.getData()
        );
    }
    public GameDetail toEntity() {
        return GameDetail.builder()
                .id(id)
                .platform(platform)
                .success(success)
                .data(data)
                .build();
    }
}

