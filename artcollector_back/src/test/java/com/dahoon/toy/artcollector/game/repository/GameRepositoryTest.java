package com.dahoon.toy.artcollector.game.repository;

import com.dahoon.toy.artcollector.game.document.Game;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository.save(new Game(101, "Zelda"));
        gameRepository.save(new Game(102, "Among Us"));
        gameRepository.save(new Game(103, "Cyberpunk"));
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }
}