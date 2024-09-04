package com.dahoon.toy.artcollector.game.service;

import com.dahoon.toy.artcollector.game.document.Game;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.mongorepository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    @Transactional
    public List<GameDto> showGameList() {
        return null;
    }

    @Transactional
    public GameDto showGameInfo(String title) {
        Game game = gameRepository.findByTitle(title).orElseThrow(() -> new IllegalArgumentException("해당 제목의 게임을 검색할 수 없습니다."));
        return new GameDto(game);
    }

    @Transactional
    public List<GameDto> searchGame(String title) {
        List<Game> gameList = gameRepository.findByTitleContaining(title).orElseThrow(() -> new IllegalArgumentException("해당 제목의 게임을 검색할 수 없습니다."));
        log.info("해당 검색어 포함 게임 조회");
        List<GameDto> gameDtoList = new ArrayList<>();
        for (Game game : gameList) {
            GameDto gameDto = new GameDto(game);
            gameDtoList.add(gameDto);
        }

        return gameDtoList;
    }
}
