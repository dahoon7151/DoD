package com.dahoon.toy.artcollector.game;

import com.dahoon.toy.artcollector.game.dto.GameDetailDto;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
            @PathVariable(value = "order") String order,
            @Parameter(description = "검색어", in = ParameterIn.QUERY)
            @RequestParam(required = false) String keyword) {
        log.info("컨트롤러 - 게임 목록 조회");

        Page<GameDto> gamePage = gameService.showGameList(page,count,order,keyword);

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
}
