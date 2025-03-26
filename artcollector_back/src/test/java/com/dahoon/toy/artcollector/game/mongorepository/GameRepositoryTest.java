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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

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

    @Test
    public void findGameByPerfectTitle() {
        //Given
        String title = "PUBG: BATTLEGROUNDS";
        Game game = gameRepository.findByTitle(title).orElse(null);

        //When

        //Then
        Assertions.assertThat(game).isNotNull();
    }

}