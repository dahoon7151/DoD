package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.game.dto.GameDetailDto;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/games")
@Validated
@Tag(name = "Game API")
public class GameController {
    private final GameService gameService;

    @GetMapping("/{page}/{count}/{order}")
    @Operation(summary = "게임 목록 조회", description = "요청한 조건에 해당하는 게임 목록을 정렬하여 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<Page<GameDto>> showGameList(
            @Parameter(description = "현재 페이지 번호")
            @PathVariable(value = "page") int page,
            @Parameter(description = "데이터 수")
            @PathVariable(value = "count") int count,
            @Parameter(description = "정렬 기준", example = "abc")
            @PathVariable(value = "order") String order) {
        log.info("컨트롤러 - 게임 목록 조회");

        Page<GameDto> gamePage = gameService.showGameList(page,count,order);

        return ResponseEntity.status(HttpStatus.OK).body(gamePage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "게임 상세정보 조회", description = "게임 Id를 통해 사용자가 선택한 게임의 상세 정보 반환")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 성공")
    })
    public ResponseEntity<GameDetailDto> showGameDetail(
            @Parameter(description = "게임 id", example = "steam_123456")
            @PathVariable(value = "id")
            @Pattern(
                    regexp = "^(steam|epic|psn)+_[a-zA-Z0-9\\\\-]+$",
                    message = "ID 형식은 platform_appid 형식이어야 하며, appid에는 영문, 숫자, 하이픈만 포함할 수 있습니다."
            ) String id) {
        log.info("컨트롤러 - 게임 상세정보 조회");

        String[] parts = id.split("_");
        GameDetailDto gameDetailDto;
        if (parts[0].equals("steam")) {
            gameDetailDto = gameService.getSteamGameDetail(id, parts[1]);
        } else {
            throw new IllegalArgumentException("유효하지 않은 platform 입니다.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(gameDetailDto);
    }

//    @GetMapping("/search/{title}")
//    @Operation(summary = "게임 검색", description = "사용자가 제목을 검색했을 때 해당 검색어가 포함된 제목의 게임 정보를 반환")
//    public ResponseEntity<List<GameDto>> searchGameName(@PathVariable(value = "title") String title) {
//        List<GameDto> gameDtoList = gameService.searchGame(title);
//
//        return ResponseEntity.status(HttpStatus.OK).body(gameDtoList);
//    }
//
//    @GetMapping("/renew/steam")
//    @Operation(summary = "Steam 데이터 갱신", description = "Steam 플랫폼의 게임 데이터를 불러와 저장")
//    public ResponseEntity<String> renewSteam() {
//        // Steam GetApp API 적용
//
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
//
//    @GetMapping("/rank/review")
//    @Operation(summary = "인기 리뷰 조회", description = "인기있는 상위 5개 리뷰의 정보 반환")
//    public ResponseEntity<?> rankReview() {
//
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
//
//    @PostMapping("/review/{gameId}")
//    @Operation(summary = "리뷰 작성", description = "게임에 대한 리뷰글 작성")
//    public ResponseEntity<?> writeReview() {
//
//        return ResponseEntity.status(HttpStatus.OK).body(null);
//    }
}
