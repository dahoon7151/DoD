package com.dahoon.toy.artcollector.game.service;

import com.dahoon.toy.artcollector.game.component.SteamApiClient;
import com.dahoon.toy.artcollector.game.document.Game;
import com.dahoon.toy.artcollector.game.document.GameDetail;
import com.dahoon.toy.artcollector.game.dto.GameDetailDto;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.message.GameMessageProducer;
import com.dahoon.toy.artcollector.game.repository.GameDetailRepository;
import com.dahoon.toy.artcollector.game.repository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @Mock
    private GameDetailRepository gameDetailRepository;
    @Mock
    private SteamApiClient steamApiClient;
    @Mock
    private GameMessageProducer gameMessageProducer;

    @InjectMocks
    private GameService gameService;

    private Game game1;
    private Game game2;
    private Page<Game> gamePage;
    private GameDetail gameDetail;

    @BeforeEach
    void setup() {
        game1 = new Game(1, "titleA");
        game2 = new Game(2, "titleB");
        gamePage = new PageImpl<>(List.of(game1, game2));

        gameDetail = new GameDetail("steam_123456", "steam", true, null);
    }

    @Test
    void showGameList_정상반환() {
        // given
        int page = 0;
        int count = 2;
        String order = "abc";

        Pageable expectedPageable = PageRequest.of(page, count, Sort.by(Sort.Order.asc("name")));
        when(gameRepository.findAll(expectedPageable)).thenReturn(gamePage);

        // when
        Page<GameDto> result = gameService.showGameList(page, count, order, keyword);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).getName()).isEqualTo("titleA");
        assertThat(result.getContent().get(1).getName()).isEqualTo("titleB");

        verify(gameRepository).findAll(expectedPageable);
    }

    @Test
    void getSteamGameDetail_DB조회() {
        // given
        String id = "steam_123456";
        String appid = "123456";

        Mockito.when(gameDetailRepository.findById(id)).thenReturn(Optional.of(gameDetail));

        // when
        GameDetailDto result = gameService.getSteamGameDetail(id, appid);

        // then
        assertEquals(id, result.getId());
        Mockito.verify(steamApiClient, Mockito.never()).fetchGameDetail(Mockito.anyLong());
        Mockito.verify(gameMessageProducer, Mockito.never()).sendGameDetailSave(Mockito.any());
    }

    @Test
    void getSteamGameDetail_메시지큐저장() {
        // given
        String id = "steam_123456";
        String appid = "123456";

        Mockito.when(gameDetailRepository.findById(id)).thenReturn(Optional.empty());
        Mockito.when(steamApiClient.fetchGameDetail(Long.parseLong(appid))).thenReturn(gameDetail);

        // when
        GameDetailDto result = gameService.getSteamGameDetail(id, appid);

        // then
        assertEquals(id, result.getId());
        Mockito.verify(steamApiClient).fetchGameDetail(Long.parseLong(appid));
        Mockito.verify(gameMessageProducer).sendGameDetailSave(gameDetail);
    }
}