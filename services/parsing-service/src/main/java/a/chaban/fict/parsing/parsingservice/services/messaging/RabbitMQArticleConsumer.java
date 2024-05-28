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
    private final RabbitMQArticleProducer rabbitMQArticleProducer;

    @RabbitListener(queues = {"${rabbitmq.parsing.queue.name}"})
    public void consume(ArticleSendDTO articleSendDTO) throws IOException, ParseException {
        LOGGER.info(String.format("Received article to translate -> %s", articleSendDTO.getArticle().toString()));
        switch (articleSendDTO.getLanguage()) {
            case "en":
                LOGGER.info("Article is in English. Translating to Ukrainian.");
                var title_ua = translateAPIParser
                        .doParse(articleSendDTO.getArticle().getTitle_en(), "en", "uk");
                LOGGER.info("Translated title: " + title_ua);
                articleSendDTO.getArticle()
                        .setTitle_ua(title_ua);
                var description_ua = translateAPIParser
                        .doParse(articleSendDTO.getArticle().getDescription_en(), "en", "uk");
                LOGGER.info("Translated description: " + description_ua);
                articleSendDTO.getArticle().setDescription_ua(description_ua);
                LOGGER.info("Translated article: " + articleSendDTO.getArticle().toString());
                rabbitMQArticleProducer.sendTranslatedArticle(articleSendDTO.getArticle());
                break;
            case "ua":
                LOGGER.info("Article is in Ukrainian. Translating to English.");
                var title_en = translateAPIParser
                        .doParse(articleSendDTO.getArticle().getTitle_ua(), "uk", "en");
                LOGGER.info("Translated title: " + title_en);
                articleSendDTO.getArticle()
                        .setTitle_en(title_en);
                var description_en = translateAPIParser
                        .doParse(articleSendDTO.getArticle().getDescription_ua(), "uk", "en");
                LOGGER.info("Translated description: " + description_en);
                articleSendDTO.getArticle().setDescription_en(description_en);
                LOGGER.info("Translated article: " + articleSendDTO.getArticle().toString());
                rabbitMQArticleProducer.sendTranslatedArticle(articleSendDTO.getArticle());
                break;
        }
    }
}
