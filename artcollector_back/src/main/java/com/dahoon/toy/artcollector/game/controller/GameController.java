package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/game")
@Tag(name = "Game API")
public class GameController {
    private final GameService gameService;

    @GetMapping("/showlist/{page}/{order}")
    @Operation(summary = "게임 리스트 조회", description = "요청한 페이지에 해당하는 게임정보들을 정렬하여 반환")
    @Parameter(name = "page", description = "현재 페이지 번호")
    @Parameter(name = "order", description = "정렬 기준", example = "abc,price,rate")
    public ResponseEntity<Page<GameDto>> showGameList(@PathVariable(value = "page") int page,
                                                      @PathVariable(value = "order") String order) {
        int count = 10; // 한 페이지에 노출되는 게임 개수
        Page<GameDto> gamePage = gameService.showGameList(page,count,order);

        return ResponseEntity.status(HttpStatus.OK).body(gamePage);
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "게임 상세정보", description = "SteamId를 통해 사용자가 선택한 게임의 상세 정보 반환")
    public ResponseEntity<GameDto> findOneGame(@PathVariable(value = "id") String steamId) {
        GameDto gameDto = gameService.showGameInfo(steamId);

        return ResponseEntity.status(HttpStatus.OK).body(gameDto);
    }

    @GetMapping("/search/{title}")
    @Operation(summary = "게임 검색", description = "사용자가 제목을 검색했을 때 해당 검색어가 포함된 제목의 게임 정보를 반환")
    public ResponseEntity<List<GameDto>> searchGameTitle(@PathVariable(value = "title") String title) {
        List<GameDto> gameDtoList = gameService.searchGame(title);

        return ResponseEntity.status(HttpStatus.OK).body(gameDtoList);
    }
}
