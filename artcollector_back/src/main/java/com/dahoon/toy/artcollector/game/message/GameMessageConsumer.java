package com.dahoon.toy.artcollector.game.message;

import com.dahoon.toy.artcollector.game.repository.GameDetailRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameMessageConsumer {
    private final GameDetailRepository gameDetailRepository;

    @RabbitListener(queues = "${rabbitmq.queue.game.save}")
    public void handleGameDetailSave(GameDetailSaveMessage message) {
        log.info("게임 상세정보 메시지큐 저장 완료: {}", message.getId());
        gameDetailRepository.save(message.toEntity());
    }
}
