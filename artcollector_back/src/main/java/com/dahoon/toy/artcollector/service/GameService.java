package com.dahoon.toy.artcollector.service;

import com.dahoon.toy.artcollector.mongo.document.Game;
import com.dahoon.toy.artcollector.dto.GameDto;
import com.dahoon.toy.artcollector.mongo.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
