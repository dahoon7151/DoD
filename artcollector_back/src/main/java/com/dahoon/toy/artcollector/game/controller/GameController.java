package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/renew/steam")
    @Operation(summary = "Steam 데이터 갱신", description = "Steam 플랫폼의 게임 데이터를 불러와 저장")
    public ResponseEntity<String> renewSteam() {
        // Steam GetApp API 적용

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @GetMapping("/rank/review")
    @Operation(summary = "인기 리뷰 조회", description = "인기있는 상위 5개 리뷰의 정보 반환")
    public ResponseEntity<?> rankReview() {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }

    @PostMapping("/review/{gameId}")
    @Operation(summary = "리뷰 작성", description = "게임에 대한 리뷰글 작성")
    public ResponseEntity<?> writeReview() {

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
