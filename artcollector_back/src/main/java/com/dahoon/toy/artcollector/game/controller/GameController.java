package com.dahoon.toy.artcollector.game.controller;

import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.service.GameService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    @GetMapping("/find/{title}")
    public ResponseEntity<GameDto> findOneGame(@PathVariable(value = "title") String title) {
        GameDto gameDto = gameService.showGameInfo(title);

        return ResponseEntity.status(HttpStatus.OK).body(gameDto);
    }
}
