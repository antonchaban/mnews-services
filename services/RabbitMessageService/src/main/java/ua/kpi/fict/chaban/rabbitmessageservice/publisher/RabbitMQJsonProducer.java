package ua.kpi.fict.chaban.rabbitmessageservice.publisher;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.User;

@Service
@RequiredArgsConstructor
public class RabbitMQJsonProducer {
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.json.key.name}")
    private String jsonRoutingKey;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQJsonProducer.class);

    public void sendJsonMessage(User user) {
        LOGGER.info(String.format("Sending json message: %s", user.toString()));
        rabbitTemplate.convertAndSend(exchangeName, jsonRoutingKey, user);
    }
}
