package a.chaban.articleservice.services;

import a.chaban.articleservice.models.Article;
import a.chaban.articleservice.models.User;
import a.chaban.articleservice.repositories.ArticleRepo;
import a.chaban.articleservice.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RabbitMQArticleConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQArticleConsumer.class);
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;

    @RabbitListener(queues = {"${rabbitmq.article.queue.name}"})
    public void consume(Article article) {
        Article existingArticle = articleRepo.findByTitle_en(article.getTitle_en());
        if (existingArticle == null) {
            User user = userRepo.findById(article.getUserId()).orElse(null);
            article.setUser(user);
            articleRepo.save(article);
            LOGGER.info(String.format("New article received and saved -> %s", article.toString()));
        } else {
            LOGGER.info(String.format("Duplicate article received -> %s. Skipping saving.", article.toString()));
        }
    }
}

