package com.dahoon.toy.artcollector.game.mongorepository;

import com.dahoon.toy.artcollector.game.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findBySteamId(String steamId);

    Optional<Game> findByTitle(String title);

//    @Query()
    Optional<List<Game>> findByTitleContaining(String title);
}
