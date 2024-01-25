package ua.kpi.fict.chaban.rabbitmessageservice.publisher;


import lombok.RequiredArgsConstructor;
import org.slf4j.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQProducer {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.routing.key.name}")
    private String routingKey;

    private final RabbitTemplate rabbitTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQProducer.class);

    public void send(String message) {
        LOGGER.info(String.format("Sending message: %s", message));
        rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
    }
}
