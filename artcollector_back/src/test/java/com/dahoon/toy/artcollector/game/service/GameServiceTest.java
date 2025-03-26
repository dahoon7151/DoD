package com.dahoon.toy.artcollector.game.service;

import com.dahoon.toy.artcollector.game.document.Game;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.mongorepository.GameRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @Mock
    private GameRepository gameRepository;
    @InjectMocks
    private GameService gameService;

    private Game game1;
    private Game game2;
    private Page<Game> gamePage;

    @BeforeEach
    void setup() {
        game1 = new Game("1", "titleA");
        game2 = new Game("2", "titleB");
        gamePage = new PageImpl<>(List.of(game1, game2));
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
        Page<GameDto> result = gameService.showGameList(page, count, order);

        // then
        assertThat(result).hasSize(2);
        assertThat(result.getContent().get(0).getTitle()).isEqualTo("titleA");
        assertThat(result.getContent().get(1).getTitle()).isEqualTo("titleB");

        verify(gameRepository).findAll(expectedPageable);
    }
}