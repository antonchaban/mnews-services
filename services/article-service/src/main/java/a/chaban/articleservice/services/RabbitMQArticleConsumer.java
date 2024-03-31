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
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitMQUserConsumer.class);
    private final ArticleRepo articleRepo;
    private final UserRepo userRepo;

    @RabbitListener(queues = {"${rabbitmq.article.queue.name}"})
    public void consume(Article article) {
        User user = userRepo.findById(article.getUserId()).orElse(null);
        article.setUser(user);
        articleRepo.save(article);
        LOGGER.info(String.format("Article received -> %s", article.toString()));
//        LOGGER.info(String.format("User received -> %s", user.toString()));
    }
}
