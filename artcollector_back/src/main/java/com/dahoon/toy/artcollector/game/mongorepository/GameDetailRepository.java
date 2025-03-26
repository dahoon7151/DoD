package com.dahoon.toy.artcollector.game.mongorepository;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GameDetailRepository extends MongoRepository<GameDetail, String> {

}
