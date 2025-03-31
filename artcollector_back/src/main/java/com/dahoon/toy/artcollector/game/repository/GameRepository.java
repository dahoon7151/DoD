package com.dahoon.toy.artcollector.game.repository;

import com.dahoon.toy.artcollector.game.document.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
//    Optional<Game> findByAppId(String steamId);
//
//    Optional<Game> findByTitle(String title);
//
//    Optional<List<Game>> findByTitleContaining(String title);

    Page<Game> findAll(Pageable pageable);
}
