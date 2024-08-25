package com.dahoon.toy.artcollector.service;

import com.dahoon.toy.artcollector.document.Game;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GameService {
    @Transactional
    public List<String> showGameList() { // 페이징 처리?
        return
    }
}
