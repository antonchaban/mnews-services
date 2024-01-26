package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQUserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUserConsumer.class);

    @RabbitListener(queues = {"${rabbitmq.user.queue.name}"})
    public void consume(UserDto user) {
        // todo save user to db
        LOGGER.info(String.format("User received -> %s", user.toString()));
    }
}
