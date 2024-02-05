package a.chaban.fict.parsing.parsingservice.services.messaging;

import a.chaban.fict.parsing.parsingservice.entities.Article;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQArticleProducer {
    @Value("${rabbitmq.article.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.article.routing.key.name}")
    private String jsonRoutingKey;


    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQArticleProducer.class);


    public void sendArticleEntity(Article article) {
        LOGGER.info(String.format("Sending json message: %s", article));
        rabbitTemplate.convertAndSend(exchangeName, jsonRoutingKey, article);
    }
}
