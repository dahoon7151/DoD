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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameService {
    private final GameDetailRepository gameDetailRepository;
    private final SteamApiClient steamApiClient;
    private final GameMessageProducer gameMessageProducer;
    private final ElasticsearchClient elasticsearchClient;

    @Transactional
    public Page<GameDto> showGameList(int page, int count, String order, String keyword) {
        Pageable pageable = PageRequest.of(page, count);

        String effectiveKeyword = (keyword == null || keyword.trim().isEmpty()) ? null : keyword.trim();

        try {
            SearchResponse<Game> response = elasticsearchClient.search(s -> {
                SearchRequest.Builder builder = s.index("game")
                        .from(page * count)
                        .size(count);

                log.info("searchRequest 생성");

                // keyword 유무에 따라 단순 조회 or 검색
                if (effectiveKeyword == null) {
                    builder.query(q -> q.matchAll(m -> m))
                            .sort(sort -> sort
                                    .field(f -> f
                                            .field(resolveSortOrder(order))
                                            .order(SortOrder.Asc)  // 정렬방향 가변적으로 추후에 수정
                                    )
                            );
                } else {
                    builder.query(q -> q.match(m -> m.field("name").query(effectiveKeyword)))
                            .sort(sort -> sort.score(ss -> ss.order(SortOrder.Desc)));
                }
                return builder;
            }, Game.class);
            log.info("search 완료");

            List<GameDto> dtoList = response.hits().hits().stream()
                    .map(Hit::source)
                    .filter(Objects::nonNull)
                    .map(GameDto::toDto)
                    .toList();

            long totalHits = response.hits().total() != null
                    ? response.hits().total().value()
                    : dtoList.size();

            return new PageImpl<>(dtoList, pageable, totalHits);

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


    private String resolveSortOrder(String order) {
        switch (order) {
            case "abc": // 이름 순
                return "name.keyword";
            default:
                throw new IllegalArgumentException("잘못된 정렬 기준입니다: " + order);
        }
    }
}
