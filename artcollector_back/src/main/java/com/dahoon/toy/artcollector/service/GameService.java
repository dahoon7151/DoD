package com.dahoon.toy.artcollector.service;

import com.dahoon.toy.artcollector.document.Game;
import com.dahoon.toy.artcollector.dto.GameDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {
    @Transactional
    public List<GameDto> showGameList() {
        return null;
    }

    @Transactional
    public GameDto showGameInfo() {
        return null;
    }
}
