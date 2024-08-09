package com.dahoon.toy.artcollector.repository;

import com.dahoon.toy.artcollector.document.Game;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameRepository extends MongoRepository<Game, String> {
}
