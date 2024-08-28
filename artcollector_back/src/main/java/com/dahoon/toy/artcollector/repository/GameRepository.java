package com.dahoon.toy.artcollector.repository;

import com.dahoon.toy.artcollector.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GameRepository extends MongoRepository<Game, String> {
    Optional<Game> findBySteamId(String steamId);

    Optional<Game> findByTitle(String title);
}
