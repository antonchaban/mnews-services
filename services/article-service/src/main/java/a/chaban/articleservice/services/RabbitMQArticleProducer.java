package a.chaban.articleservice.services;

import a.chaban.articleservice.dtos.ArticleSendDTO;
import a.chaban.articleservice.models.Article;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQArticleProducer {
    @Value("${rabbitmq.parsing.exchange.name}")
    private String exchangeName;

    @Value("${rabbitmq.parsing.routing.key.name}")
    private String jsonRoutingKey;


    private final RabbitTemplate rabbitTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQArticleProducer.class);


    public void sendArticleEntityToTranslate(ArticleSendDTO article) {
        LOGGER.info(String.format("Sending json message: %s", article));
        rabbitTemplate.convertAndSend(exchangeName, jsonRoutingKey, article);
    }


}
