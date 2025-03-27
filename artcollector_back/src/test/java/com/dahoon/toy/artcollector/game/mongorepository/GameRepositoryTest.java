package com.dahoon.toy.artcollector.game.mongorepository;

import com.dahoon.toy.artcollector.ArtcollectorApplication;
import com.dahoon.toy.artcollector.game.document.Game;
import org.aspectj.lang.annotation.After;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@DataMongoTest
@ActiveProfiles("test")
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    void setUp() {
        gameRepository.save(new Game("101", "Zelda"));
        gameRepository.save(new Game("102", "Among Us"));
        gameRepository.save(new Game("103", "Cyberpunk"));
    }

    @AfterEach
    void tearDown() {
        gameRepository.deleteAll();
    }

    @Test
    void findAll_정상조회() {
        // given
        int page = 0;
        int size = 2;
        Sort sort = Sort.by(Sort.Order.asc("name"));
        Pageable pageable = PageRequest.of(page, size, sort);

        // when
        Page<Game> result = gameRepository.findAll(pageable);

        // then
        assertThat(result.getContent()).hasSize(2);
        assertThat(result.getTotalElements()).isEqualTo(3);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Among Us");
        assertThat(result.getContent().get(1).getName()).isEqualTo("Cyberpunk");
    }

    @Test
    void findAll_두번째페이지() {
        // given
        Pageable pageable = PageRequest.of(1, 2, Sort.by(Sort.Order.asc("name")));

        // when
        Page<Game> result = gameRepository.findAll(pageable);

        // then
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Zelda");
    }
}