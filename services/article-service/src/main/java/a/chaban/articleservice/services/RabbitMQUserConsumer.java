package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.UserDto;
import a.chaban.articleservice.models.User;
import a.chaban.articleservice.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQUserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUserConsumer.class);
    private final UserRepo userRepo;

    @RabbitListener(queues = {"${rabbitmq.user.queue.name}"})
    public void consume(User user) {
        userRepo.save(user);
        LOGGER.info(String.format("User received -> %s", user.toString()));
    }
}
