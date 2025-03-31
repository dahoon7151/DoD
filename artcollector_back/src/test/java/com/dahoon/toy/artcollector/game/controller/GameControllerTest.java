package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.common.config.SecurityConfig;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
@Import(SecurityConfig.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    void showGameList_성공() throws Exception {
        // given
        int page = 1;
        String order = "abc";
        int count = 10;

        List<GameDto> gameList = List.of(
                new GameDto("1L", "Game A"),
                new GameDto("2L", "Game B")
        );
        Page<GameDto> gamePage = new PageImpl<>(gameList, PageRequest.of(page, count), gameList.size());

        given(gameService.showGameList(page, count, order)).willReturn(gamePage);

        // when & then
        mockMvc.perform(get("/api/games/showlist/{page}/{order}", page, order))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Game A"))
                .andExpect(jsonPath("$.content[1].name").value("Game B"))
                .andExpect(jsonPath("$.content.length()").value(2));
    }
}