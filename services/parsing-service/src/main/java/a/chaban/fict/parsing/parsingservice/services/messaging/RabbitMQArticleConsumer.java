package a.chaban.fict.parsing.parsingservice.services.messaging;

import a.chaban.fict.parsing.parsingservice.entities.ArticleSendDTO;
import a.chaban.fict.parsing.parsingservice.services.translation.TranslateAPIParser;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class RabbitMQArticleConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQArticleConsumer.class);
    private final TranslateAPIParser translateAPIParser;

    @RabbitListener(queues = {"${rabbitmq.article.queue.name}"})
    public void consume(ArticleSendDTO articleSendDTO) throws IOException, ParseException { // todo send to article service
        LOGGER.info(String.format("Received article to translate -> %s", articleSendDTO.getArticle().toString()));
        switch (articleSendDTO.getLanguage()) {
            case "en":
                LOGGER.info("Article is in English. Translating to Ukrainian.");
                articleSendDTO.getArticle()
                        .setTitle_ua(translateAPIParser
                                .doParse(articleSendDTO.getArticle().getTitle_en(), "en", "ua"));
                articleSendDTO.getArticle().setDescription_ua(translateAPIParser
                        .doParse(articleSendDTO.getArticle().getDescription_en(), "en", "ua"));
                break;
            case "ua":
                LOGGER.info("Article is in Ukrainian. Translating to English.");
                articleSendDTO.getArticle()
                        .setTitle_en(translateAPIParser
                                .doParse(articleSendDTO.getArticle().getTitle_ua(), "ua", "en"));
                articleSendDTO.getArticle().setDescription_en(translateAPIParser
                        .doParse(articleSendDTO.getArticle().getDescription_ua(), "ua", "en"));
                break;
        }
    }
}
