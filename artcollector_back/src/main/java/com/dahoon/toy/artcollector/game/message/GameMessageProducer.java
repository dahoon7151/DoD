package com.dahoon.toy.artcollector.game.message;

import com.dahoon.toy.artcollector.game.document.GameDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameMessageProducer {
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.game}")
    private String exchange;

    @Value("${rabbitmq.routing.game.save}")
    private String routingKey;

    public void sendGameDetailSave(GameDetail gameDetail) {
        GameDetailSaveMessage message = GameDetailSaveMessage.toDto(gameDetail);
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
