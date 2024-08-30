package com.dahoon.toy.artcollector.repository;

import com.dahoon.toy.artcollector.ArtcollectorApplication;
import com.dahoon.toy.artcollector.mongo.document.Game;
import com.dahoon.toy.artcollector.mongo.repository.GameRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ContextConfiguration(classes = ArtcollectorApplication.class)
@ActiveProfiles("test")
@Transactional
class GameRepositoryTest {

    @Autowired
    private GameRepository gameRepository;

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