package com.dahoon.toy.artcollector.game.repository;

import com.dahoon.toy.artcollector.game.document.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends ElasticsearchRepository<Game, Long> {
}
