package com.a.chaban.uservice.userservice.services;

import com.a.chaban.uservice.userservice.dtos.UserDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQUserProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.json.key.name}")
    private String jsonRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUserProducer.class);

    public void sendJsonMessage(UserDto user) {
        LOGGER.info(String.format("Sending json message: %s", user.toString()));
        rabbitTemplate.convertAndSend(exchangeName, jsonRoutingKey, user);
    }
}