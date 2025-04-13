package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.common.config.SecurityConfig;
import com.dahoon.toy.artcollector.game.dto.GameDetailDto;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
        int count = 10;
        String order = "abc";

        List<GameDto> gameList = List.of(
                new GameDto(1, "Game A"),
                new GameDto(2, "Game B")
        );
        Page<GameDto> gamePage = new PageImpl<>(gameList, PageRequest.of(page, count), gameList.size());

        given(gameService.showGameList(page, count, order)).willReturn(gamePage);

        // when & then
        mockMvc.perform(get("/api/games/{page}/{count}/{order}", page, count, order))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].appId").value(1))
                .andExpect(jsonPath("$.content[0].name").value("Game A"))
                .andExpect(jsonPath("$.content[1].name").value("Game B"))
                .andExpect(jsonPath("$.content.length()").value(2));
    }

    @Test
    void showGameDetail_정상조회() throws Exception {
        // given
        String id = "steam_123456";
        GameDetailDto mockDto = new GameDetailDto("steam_123456", "steam", true, null);

        Mockito.when(gameService.getSteamGameDetail(id, "123456")).thenReturn(mockDto);

        // when & then
        mockMvc.perform(get("/api/games/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

        Mockito.verify(gameService).getSteamGameDetail(id, "123456");
    }

    @Test
    void showGameDetail_Valid에러() throws Exception {
        // given
        String id = "steam123";

        // when & then
        mockMvc.perform(get("/api/games/" + id))
                .andExpect(status().isBadRequest());
    }
}