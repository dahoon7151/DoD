package com.dahoon.toy.artcollector.repository;

import com.dahoon.toy.artcollector.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
