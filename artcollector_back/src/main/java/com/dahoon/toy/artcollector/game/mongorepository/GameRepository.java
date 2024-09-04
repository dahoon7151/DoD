package com.dahoon.toy.artcollector.game.mongorepository;

import com.dahoon.toy.artcollector.game.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findBySteamId(String steamId);

    Optional<Game> findByTitle(String title);
}
