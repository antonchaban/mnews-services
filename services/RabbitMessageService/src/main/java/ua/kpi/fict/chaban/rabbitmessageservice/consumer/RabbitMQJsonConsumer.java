package ua.kpi.fict.chaban.rabbitmessageservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import ua.kpi.fict.chaban.rabbitmessageservice.entities.User;

@Service
public class RabbitMQJsonConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.queue.json.name}"})
    public void consume(User user) {
        LOGGER.info(String.format("Json message received: %s", user.toString()));
    }
}
