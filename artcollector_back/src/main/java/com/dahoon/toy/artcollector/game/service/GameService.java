package com.dahoon.toy.artcollector.game.service;

import com.dahoon.toy.artcollector.game.document.Game;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.mongorepository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<GameDto> showGameList(int page, int count, String order) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (order.equals("abc")) {
            sorts.add(Sort.Order.asc("title"));
        } else if (order.equals("price")) {
            sorts.add(Sort.Order.asc("price"));
//        } else if (order.equals("rate")) {  // 기준 추후 추가
//            sorts.add(Sort.Order.desc("rate"));
        } else {
            throw new IllegalArgumentException("잘못된 정렬 기준");
        }
        Pageable pageable = PageRequest.of(page, count, Sort.by(sorts));
        Page<Game> gamePages = gameRepository.findAll(pageable);

        return gamePages.map(GameDto::new);
    }
    @Transactional
    public GameDto showGameInfo(String steamId) {
        Game game = gameRepository.findBySteamId(steamId).orElseThrow(() -> new IllegalArgumentException("해당 SteamId의 게임을 검색할 수 없습니다."));
        return new GameDto(game);
    }

    @Transactional
    public List<GameDto> searchGame(String title) {
        List<Game> gameList = gameRepository.findByTitleContaining(title).orElseThrow(() -> new IllegalArgumentException("해당 제목의 게임을 검색할 수 없습니다."));
        log.info("해당 검색어 포함 게임 조회");
        List<GameDto> gameDtoList = new ArrayList<>();
        for (Game game : gameList) {
            GameDto gameDto = new GameDto(game); // 필요한 정보만 반환하도록 수정 필요 (제목,이미지)
            gameDtoList.add(gameDto);
        }

        return gameDtoList;
    }
}
