package com.dahoon.toy.artcollector.game.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
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
import java.util.Objects;
import java.util.stream.Collectors;

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

        String effectiveKeyword = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

        try {
            SearchResponse<Game> response = elasticsearchClient.search(s -> {
                SearchRequest.Builder builder = s.index("game")
                        .from(page * count)
                        .size(count)
                        .sort(sort -> sort
                                .field(f -> f
                                        .field("name.keyword")
                                        .order(SortOrder.Asc)
                                )
                        );

                // keyword 유무에 따라 단순 조회 or 검색
                if (effectiveKeyword == null) {
                    builder.query(q -> q.matchAll(m -> m));
                } else {
                    builder.query(q -> q.match(m -> m.field("name").query(effectiveKeyword)));
                }

                return builder;
            }, Game.class);

            List<GameDto> dtos = response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(GameDto::toDto)
                    .toList();

            long totalHits = response.hits().total() != null
                    ? response.hits().total().value()
                    : dtos.size();

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
