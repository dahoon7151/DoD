package com.dahoon.toy.artcollector.game.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import com.dahoon.toy.artcollector.game.component.SteamApiClient;
import com.dahoon.toy.artcollector.game.document.Game;
import com.dahoon.toy.artcollector.game.document.GameDetail;
import com.dahoon.toy.artcollector.game.dto.GameDetailDto;
import com.dahoon.toy.artcollector.game.dto.GameDto;
import com.dahoon.toy.artcollector.game.message.GameMessageProducer;
import com.dahoon.toy.artcollector.game.repository.GameDetailRepository;
import com.dahoon.toy.artcollector.game.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameRepository gameRepository;
    private final GameDetailRepository gameDetailRepository;
    private final SteamApiClient steamApiClient;
    private final GameMessageProducer gameMessageProducer;
    private final ElasticsearchClient elasticsearchClient;

    @Transactional
    public Page<GameDto> showGameList(int page, int count, String order, String keyword) {
        List<Sort.Order> sorts = new ArrayList<>();
        if (order.equals("abc")) {
            sorts.add(Sort.Order.asc("name"));
        } else {
            throw new IllegalArgumentException("잘못된 정렬 기준");
        }
        Pageable pageable = PageRequest.of(page, count, Sort.by(sorts));

        if (keyword == null || keyword.trim().isEmpty()) {
            return gameRepository.findAll(pageable).map(GameDto::toDto);
        }

        try {
            SearchResponse<Game> response = elasticsearchClient.search(s -> s
                            .index("game")
                            .query(q -> q
                                    .match(m -> m
                                            .field("name")
                                            .query(keyword)
                                    )
                            )
                            .from(page * count)
                            .size(count),
                    Game.class
            );

            List<GameDto> dtos = response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(GameDto::toDto)
                    .collect(Collectors.toList());

            long totalHits = response.hits().total() != null
                    ? response.hits().total().value()
                    : dtos.size(); // fallback

            return new PageImpl<>(dtos, pageable, totalHits);

        } catch (IOException e) {
            throw new RuntimeException("Elasticsearch 검색 실패", e);
        }
    }

    @Transactional
    public GameDetailDto getSteamGameDetail(String id, String appid) {
        GameDetail gameDetail = gameDetailRepository.findById(id)
                .orElseGet(() -> {
                    GameDetail newGameDetail = steamApiClient.fetchGameDetail(Long.valueOf(appid));
                    gameMessageProducer.sendGameDetailSave(newGameDetail);

                    return newGameDetail;
                });

        return GameDetailDto.toDto(gameDetail);
    }

//    @Transactional
//    public List<GameDto> searchGame(String title) {
//        List<Game> gameList = gameRepository.findByTitleContaining(title).orElseThrow(() -> new IllegalArgumentException("해당 제목의 게임을 검색할 수 없습니다."));
//        log.info("해당 검색어 포함 게임 조회");
//        List<GameDto> gameDtoList = new ArrayList<>();
//        for (Game game : gameList) {
//            GameDto gameDto = new GameDto(game); // 필요한 정보만 반환하도록 수정 필요 (제목,이미지)
//            gameDtoList.add(gameDto);
//        }
//
//        return gameDtoList;
//    }
}
